# coding=utf-8
import os

import sys
import json
import base64
import time
import librosa
import ffmpeg
import speech_recognition as sr
import uuid

IS_PY3 = sys.version_info.major == 3

if IS_PY3:
    from urllib.request import urlopen
    from urllib.request import Request
    from urllib.error import URLError
    from urllib.parse import urlencode

    timer = time.perf_counter

# Can be removed, I'm now using ffmpeg directly
# def resample_rate(path,new_sample_rate = 16000):
#
#     signal, sr = librosa.load(path, sr=None)
#     wavfile = path.split('/')[-1]
#     wavfile = wavfile.split('.')[0]
#     file_name = '../voice_tmp/' + wavfile + '.wav'
#     new_signal = librosa.resample(signal, sr, new_sample_rate) #
#     librosa.output.write_wav(file_name, new_signal , new_sample_rate)


API_KEY = ''
SECRET_KEY = ''





CUID = '123456PYTHON'
# 采样率
RATE = 16000  # 固定值

# 普通版

DEV_PID = 1537  # 1537 表示识别普通话，使用输入法模型。根据文档填写PID，选择语言及识别模型
ASR_URL = 'http://vop.baidu.com/server_api'
SCOPE = 'audio_voice_assistant_get'  # 有此scope表示有asr能力，没有请在网页里勾选，非常旧的应用可能没有


class DemoError(Exception):
    pass


"""  TOKEN start """

TOKEN_URL = ''


def fetch_token():
    params = {'grant_type': 'client_credentials',
              'client_id': API_KEY,
              'client_secret': SECRET_KEY}
    post_data = urlencode(params)
    if (IS_PY3):
        post_data = post_data.encode('utf-8')
    req = Request(TOKEN_URL, post_data)
    try:
        f = urlopen(req)
        result_str = f.read()
    except URLError as err:
        print('token http response http code : ' + str(err.code))
        result_str = err.read()
    if (IS_PY3):
        result_str = result_str.decode()

    # print(result_str)
    result = json.loads(result_str)
    # print(result)
    if ('access_token' in result.keys() and 'scope' in result.keys()):
        # print(SCOPE)
        if SCOPE and (not SCOPE in result['scope'].split(' ')):  # SCOPE = False 忽略检查
            raise DemoError('scope is not correct')
        # print('SUCCESS WITH TOKEN: %s  EXPIRES IN SECONDS: %s' % (result['access_token'], result['expires_in']))
        return result['access_token']
    else:
        raise DemoError('MAYBE API_KEY or SECRET_KEY not correct: access_token or scope not found in token response')


"""  TOKEN end """


def baiduASRImpl():
    token = fetch_token()

    # obtain audio from the microphone
    # 从麦克风记录数据
    r = sr.Recognizer()
    with sr.Microphone() as source:
        print("Say something!")
        r.adjust_for_ambient_noise(source)
        audio = r.listen(source)

    # 将数据保存到wav文件中
    with open("voice_tmp/Recording.wav", "wb") as f:
        f.write(audio.get_wav_data(convert_rate=16000))

    # 需要识别的文件
    AUDIO_FILE_OLD = 'voice_tmp/Recording.wav'  # 只支持 pcm/wav/amr 格式，极速版额外支持m4a 格式
    AUDIO_FILE = 'voice_tmp/Recording_Resampled' + str(uuid.uuid1()) + '.wav'
    ffmpeg.input(AUDIO_FILE_OLD).output(AUDIO_FILE, ar=16000).run()

    # 文件格式
    FORMAT = AUDIO_FILE[-3:]  # 文件后缀只支持 pcm/wav/amr 格式，极速版额外支持m4a 格式

    speech_data = []
    with open(AUDIO_FILE, 'rb') as speech_file:
        speech_data = speech_file.read()

    length = len(speech_data)
    if length == 0:
        raise DemoError('file %s length read 0 bytes' % AUDIO_FILE)
    speech = base64.b64encode(speech_data)
    if (IS_PY3):
        speech = str(speech, 'utf-8')
    params = {'dev_pid': DEV_PID,
              'format': FORMAT,
              'rate': RATE,
              'token': token,
              'cuid': CUID,
              'channel': 1,
              'speech': speech,
              'len': length
              }
    post_data = json.dumps(params, sort_keys=False)
    # print post_data
    req = Request(ASR_URL, post_data.encode('utf-8'))
    req.add_header('Content-Type', 'application/json')
    try:
        begin = timer()
        f = urlopen(req)
        result_str = f.read()
        print("Request time cost %f" % (timer() - begin))
    except URLError as err:
        print('asr http response http code : ' + str(err.code))
        result_str = err.read()

    result_str = str(result_str, 'utf-8')
    parse_result = json.loads(result_str)
    return parse_result['result'][0]  # Might be a list of results?


# For Testing Purpose
if __name__ == '__main__':
    print(baiduASRImpl())

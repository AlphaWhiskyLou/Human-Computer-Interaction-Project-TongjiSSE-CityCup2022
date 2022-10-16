from service.BaiduASR import baiduASRImpl
from service.intention_reference import IntentionReference
def TextProcessing():
    handler = IntentionReference();
    # 调用百度语音识别API
    input = baiduASRImpl()
    intention = handler.make_reference(input)
    print(intention)
    return intention

    #Demo returning value from BaiduASR
#You can just use this function to get the result from BaiduASR, and the result should be a string with punctuation.
# TODO: Adapt this file to the new voice recorder

import os

from flask_restful import Resource, fields, Api
from flask import Flask, request
from flask_restplus import Namespace, Resource, fields, reqparse
# from start import app
from werkzeug.datastructures import FileStorage
from werkzeug.utils import secure_filename

from model.ASRModel import asr_model

api = Namespace('asr_namespace', description='ASR Namespace')
triangle_model = api.model('asr', model=asr_model)

parser = reqparse.RequestParser()
parser.add_argument('data', type=int, required=True, help='data cannot be converted.')
parser.add_argument('name', type=str)

# upload parser
upload_parser = api.parser()
upload_parser.add_argument('file', location='files',
                           type=FileStorage, required=True)


@api.route('/asr', methods=['POST'])
@api.expect(upload_parser)
class TriangleBasic(Resource):
    def post(self):
        file = request.files['file']
        if file:
            print(file.filename)
        base = os.path.dirname(__file__)  # Current location
        upload_path = os.path.join(base, '..\\voice_tmp', secure_filename(file.filename))  # Need to create folder first
        file.save(upload_path)
        return

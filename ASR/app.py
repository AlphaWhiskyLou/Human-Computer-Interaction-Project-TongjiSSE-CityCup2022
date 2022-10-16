from flask import Flask, request, jsonify
from flask_cors import cross_origin, CORS



app = Flask(__name__)
app.config['CORS_HEADERS'] = 'Content-Type'

CORS(app, resources={r"/*": {"origins": "*"}})
app.config['CORS_HEADERS'] = 'Content-Type'


@app.route('/audioService', methods=['get'])
def AudioService():
    if request.method == 'GET':
        print("AudioService")
        from service.TextProcessing import TextProcessing
        responseString = TextProcessing()
        return jsonify(responseString)

    return jsonify('responseString')


@app.after_request
def after_request(response):
    response.headers.add('Access-Control-Allow-Credentials', 'true')
    return response


if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True, port=5000, threaded=True)

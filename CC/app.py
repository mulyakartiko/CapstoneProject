# https://cloud.google.com/storage/docs/reference/libraries#command-line

import os
from flask import Flask, flash, request, redirect, url_for, Response
from werkzeug.utils import secure_filename
#from google.cloud import storage
from google.oauth2 import service_account
import requests
import json
#import uuid, shortuuid
from mlapp import predict as detect

UPLOAD_FOLDER = 'D:\Bangkit 2021\Cloud API\MLAPI\asset'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = 'asset'

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/detect', methods=['POST'])
def upload_file():
    if request.method == 'POST':
        # check if the post request has the file part
        if 'file' not in request.files:
            flash('No file part')
            # return redirect(request.url)
            return Response("{'Error':'there is no file part'}", status=400, mimetype='application/json')
        file = request.files['file']
        # if user does not select file, browser also
        # submit an empty part without filename
        if file.filename == '':
            flash('No selected file')
            # return redirect(request.url)
            return Response("{'Error':'user does not select file'}", status=400, mimetype='application/json')
        if file and allowed_file(file.filename):
            # Generate unique name
            newname = str(uuid.uuid4()) + '-' + str(shortuuid.uuid()) + '-' + file.filename
            filename = secure_filename(newname)

            # Save file ke direktori tmp
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            
            # Memanggil Program ML
            path_foto = "asset/{}".format(newname)
            hasil = detect(path_foto)
            
            # Hapus gambar
            os.system("rm {}".format(path_foto))

            result = {"file": str(filename), "result": hasil}
            json_result = json.dumps(result)

            return Response(json_result, status=200, mimetype='application/json')

    return Response("{'Error':'input does not match the rules.'}", status=400, mimetype='application/json')
    

if __name__ == '__main__':
    server_port = os.environ.get('PORT', '8080')
    app.run(debug=False, port=server_port, host='0.0.0.0')

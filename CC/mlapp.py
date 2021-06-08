import json
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.models import Sequential
from tensorflow.keras import layers
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.optimizers import Adam, RMSprop, SGD
import numpy as np
import pandas as pd

model_json = open('D:\Bangkit 2021\Cloud Run\Chili-predict\model.json','r').read()
model = keras.models.model_from_json(model_json)
model.load_weights('D:\Bangkit 2021\Cloud Run\Chili-predict\model.h5')

def predict(foto):
	i = image.load_img(foto, target_size=(100, 100))
	i = image.img_to_array(i)
	i = i.ImageDataGenerator(rescale=1./255)
	output = model.predict_classes(i)
	label = {0:'Healthy', 1:'Leaf Curl', 2:'Leaf Spot', 3:'Whitefly', 4:'Yellowish'}
	max_val = output[0]
	idx = 0

	for i in range(1, len(output)):
		if output[i] > max_val :
			max_val = output[i]
			idx = i
	return label[idx]
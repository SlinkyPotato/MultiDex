import requests
import json

dataString = 'type'
startId = 1
endId = 3
baseUrl = 'https://pokeapi.co/api/v2/'

for dataId in range(startId, endId):
    # Call the poke moves request
    dateJson = requests.get(baseUrl + dataString + '/' + str(dataId)).json()
    fileName = 'poke_'+ dataString + '_'+ str(dataId) + '.json'
    with open('../in/' + fileName, 'x') as dataFile:
        json.dump(dateJson, dataFile)
        print(fileName)
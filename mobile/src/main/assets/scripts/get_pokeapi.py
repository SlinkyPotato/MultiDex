import requests
import json

dataString = 'contest-type'
startId = 1
endId = None
baseUrl = 'http://localhost:8000/api/v2/'

while True:
    # Call the poke moves request
    url = baseUrl + dataString + '/' + str(startId)
    response = requests.get(url)

    if response.status_code == requests.codes.not_found:
        print('Finished.')
        break

    dataJson = response.json()

    fileName = 'poke_'+ dataString + '_'+ str(startId) + '.json'
    with open('../in/' + fileName, 'w') as dataFile:
        json.dump(dataJson, dataFile)
        print(fileName)

    startId += 1

    if endId != None and startId >= endId:
        print('Finished.')
        break

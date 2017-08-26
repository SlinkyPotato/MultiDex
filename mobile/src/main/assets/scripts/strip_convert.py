import json
import argparse

parser = argparse.ArgumentParser()
parser.add_argument('data_type', help = 'The pokeapi data type to request from.')
dataString = parser.parse_args().data_type

moves = {}

with open('../in/' + dataString + '.json', 'r') as moveFile:
    jsonArray = json.load(moveFile)
    for moveObj in jsonArray:
        moveName = moveObj['name']
        # moveName = moveObj['name'].lower()
        # moveName = moveName.replace(' ', '-')
        moves[moveName] = moveObj['id']

with open('../out/' + dataString + '.py', 'w') as movePy:
    json.dump(moves, movePy)

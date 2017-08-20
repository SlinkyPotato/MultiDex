import requests
import json

from shared import *

data_type = 'berry-flavor'

def read_poke_move(startIndex, lastpokeDataId):
    for pokeId in range(startIndex, lastpokeDataId):
        try:
            with open('../in/poke_' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
                pokeData = json.loads(dataFile.read())
                
                # Move Localization
                for lang in fileLangs:
                    name = getLocalText(lang, 'name', pokeData['names'])
                    dataLocal = {'id': pokeId, 'name': name}
                    pokeDataLangs[lang].append(dataLocal)
                    
                print('pokeId: ' + str(pokeId))
        except FileNotFoundError:
            break

     # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/' + data_type + '_' + lang + '.json', 'w') as langFile:
            json.dump(pokeDataLangs[lang], langFile)
        
def getFlavorPotency(flavors, flavor):
    for flavorObj in flavors:
        if flavorObj['flavor'] == None or flavorObj['flavor']['name'] == None:
            continue
        if flavorObj['flavor']['name'] == flavor:
            return flavorObj['potency']
    return None

if __name__ == '__main__':
    read_poke_move(1, 65)

import requests
import json

from shared import *

data_type = 'characteristics'

def read_poke_move():
    pokeId = 1
    while True:
        try:
            with open('../in/' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
                pokeData = json.loads(dataFile.read())

                # Stats
                highestStatId = pokeData['highest_stat']['name']
                possibleValues = getPossibleValues(pokeData['possible_values'])
                geneModulo = pokeData['gene_modulo']

                stats = {'characteristicId': pokeId, 'geneModulo': geneModulo, 'possibleIVs': possibleValues}
                pokeDataLangs['stats'].append(stats)

                # Move Localization
                for lang in fileLangs:
                    description = getLocalText(lang, 'description', pokeData['descriptions'])
                    dataLocal = {'id': pokeId, 'description': description}

                    pokeDataLangs[lang].append(dataLocal)
                    
                print('pokeId: ' + str(pokeId))
            pokeId += 1
        except FileNotFoundError:
            break

    with open('../out/' + data_type + '_stats.json', 'w') as statsFile:
        json.dump(pokeDataLangs['stats'], statsFile)

     # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/' + data_type + '_' + lang + '.json', 'w') as langFile:
            json.dump(pokeDataLangs[lang], langFile)

def getPossibleValues(values):
    valuesString = ''
    for value in values:
        valuesString += str(value) + ','
    return valuesString

if __name__ == '__main__':
    read_poke_move()

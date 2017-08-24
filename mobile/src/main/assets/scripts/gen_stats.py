import requests
import json

from shared import *

data_type = 'stat'

def read_poke_move():
    pokeId = 1
    while True:
        try:
            with open('../in/' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
                pokeData = json.loads(dataFile.read())

                # Stats
                isBattleOnly = pokeData['is_battle_only']
                characteristics = getCharacteristics(pokeData['characteristics'])
                affectingMoves = None # might not need data
                affectingNatures = None # might not need data
                stats = {'statId': pokeId, 'affectingMoves': affectingMoves, 'affectingNatures': affectingNatures}

                pokeDataLangs['stats'].append(stats)

                # Move Localization
                for lang in fileLangs:
                    name = getLocalText(lang, 'name', pokeData['names'])
                    description = None
                    dataLocal = {'id': pokeId, 'name': name, 'description': description}

                    pokeDataLangs[lang].append(dataLocal)
                    
                print('pokeId: ' + str(pokeId))
            pokeId += 1
        except FileNotFoundError:
            break

    with open('../out/poke_stats.json', 'w') as statsFile:
        json.dump(pokeDataLangs['stats'], statsFile)

     # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/poke_stats_' + lang + '.json', 'w') as langFile:
            json.dump(pokeDataLangs[lang], langFile)
    
def getCharacteristics(characteristics):
    charNumbers = []
    for character in characteristics:
        charId = character['url'][-2:][:1]
        charNumbers.append(charId)
    return charNumbers

if __name__ == '__main__':
    read_poke_move()

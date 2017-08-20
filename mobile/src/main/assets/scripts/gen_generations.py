import requests
import json

from shared import *

data_type = 'generation'

def read_poke_move():
    pokeId = 1
    while True:
        try:
            with open('../in/poke_' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
                pokeData = json.loads(dataFile.read())

                # Stats
                game_versions = getVersionGroups(pokeData['version_groups'])
                stats = {'genId': pokeId, 'gameVersions': game_versions}
                pokeDataLangs['stats'].append(stats)

                # Move Localization
                for lang in fileLangs:
                    name = getLocalText(lang, 'name', pokeData['names'])
                    dataLocal = {'id': pokeId, 'name': name}
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
        
def getVersionGroups(version_groups):
    names = []
    for group in version_groups:
        names.append(group['name'])
    return names

if __name__ == '__main__':
    read_poke_move()

import requests
import json

from shared import *

data_type = 'ability'

def read_poke_move():
    pokeId = 1
    while True:
        try:
            with open('../in/poke_' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
                pokeData = json.loads(dataFile.read())

                # Stats
                genId = getObjName(pokeData['generation'], 'name')
                stats = {'abilityId': pokeId, 'genId': genId}
                pokeDataLangs['stats'].append(stats)

                # Move Localization
                for lang in fileLangs:
                    name = getLocalText(lang, 'name', pokeData['names'])
                    effects = getGenEffects(lang, pokeData['flavor_text_entries'], pokeId)
                    battle_effect = getLocalText(lang, 'short_effect', pokeData['effect_entries'])
                    description = getLocalText(lang, 'effect', pokeData['effect_entries'])
                    dataLocal = {'id': pokeId, 'name': name, 'effects': effects, 'battle_effect': battle_effect, 'description': description}

                    pokeDataLangs[lang].append(dataLocal)
                    
                print('pokeId: ' + str(pokeId))
            pokeId += 1
        except FileNotFoundError:
            break

    with open('../out/' + data_type + '_stats.json', 'w') as statsFile:
        json.dump(pokeDataLangs['stats'], statsFile)

     # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/abilities_' + lang + '.json', 'w') as langFile:
            json.dump(pokeDataLangs[lang], langFile)
    
def getGenEffects(lang, flavorTexts, abilityId):
    langEffects = []
    for text in flavorTexts:
        if text['language']['name'] == lang:
            effectObj = {'effect': text['flavor_text'], 'genId': text['version_group']['name']}
            langEffects.append(effectObj)
    return langEffects

if __name__ == '__main__':
    read_poke_move()

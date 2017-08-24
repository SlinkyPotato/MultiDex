import requests
import json

from shared import *

data_type = 'pokemon'

def read_poke_move(startIndex, lastpokeDataId):
    for pokeId in range(startIndex, lastpokeDataId):
        with open('../in/' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
            pokeData = json.loads(dataFile.read())

            # Base stats
            slot1AbilityId = getAbility(pokeData['abilities'], 1)
            slot2AbilityId = getAbility(pokeData['abilities'], 2)
            slot3AbilityId = getAbility(pokeData['abilities'], 3)
            xpGain = pokeData['base_experience']
            height = pokeData['height']
            weight = pokeData['weight']
            moves = getMoves(pokeData['moves'])
            heldItems = getItems(pokeData['held_items'])
            speciesId = None
            sprites = pokeData['sprites']
            type1Id = getType(pokeData['types'], 1)
            type2Id = getType(pokeData['types'], 2)
            special = None # Gen 1 only
            stats = getStats(pokeData['stats'])

            pokeBaseStats = {'pokeId': pokeId, 'xpGain': xpGain, 'height': height, 'weight': weight, 'abilitySlot1': slot1AbilityId, 'abilitySlot2': slot2AbilityId, 'abilitySlot3': slot3AbilityId, 'moves': moves, 'heldItems': heldItems, 'speciesId': speciesId, 'sprites': sprites, 'type1Id': type1Id, 'type2Id': type2Id, 'specialStat': special, 'stats': stats}
            
            pokeDataLangs['stats'].append(pokeBaseStats)

            # Move Localization
            for lang in fileLangs:
                if lang == 'en':
                    name = pokeData['name']
                else:
                    name = None
                dataLocal = {'id': pokeId, 'name': name, 'description': None}
                with open('../out/pokemon_' + lang + '.json', 'w') as langFile:
                    langFile.write(str(dataLocal))
                    exit()
            print('pokeId: ' + str(pokeId))

    # Write to stats file
    with open('../out/' + data_type + '_stats.json', 'w') as stats:
        json.dump(pokeDataLangs['stats'], stats)

def getStats(pokeStats):
    stats = []
    for stat in pokeStats:
        genId = 1
        statId = stat['stat']['name']
        ev = stat['effort']
        base = stat['base_stat']
        stats.append({'statId': statId, 'genId': genId, 'ev': ev, 'base': base})
    return stats

def getAbility(abilities, slot):
    for ability in abilities:
        if ability['slot'] == slot:
            return ability['ability']['name']
    return None

def getMoves(pokeMoves):
    moves = []
    for move in pokeMoves:
        moveId = move['move']['name']
        learnedIn = []
        for gameVersion in move['version_group_details']:
            learnedAtLvl = gameVersion['level_learned_at']
            moveMethodId = gameVersion['move_learn_method']['name']
            gvId = gameVersion['version_group']['name']
            learnedIn.append({'learnedAtLvl': learnedAtLvl, 'moveMethodId': moveMethodId, 'gvId': gvId})
        moves.append({'moveId': moveId, 'versions': learnedIn})
    return moves

def getItems(heldItems):
    items = []
    for item in heldItems:
        itemId = item['item']['name']
        obtainedIn = []
        for gameVersion in item['version_details']:
            gameVersionId = gameVersion['version']['name']
            rarity = gameVersion['rarity']
            obtainedIn.append({'gvId': gameVersionId, 'rarity': rarity})
        items.append({'itemId': itemId, 'versions': obtainedIn})

def getType(pokeTypes, slot):
    for type in pokeTypes:
        if type['slot'] == slot:
            return type['type']['name']

if __name__ == '__main__':
    read_poke_move(1, 65)

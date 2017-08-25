import json
import re
import requests

from shared import *

data_type = 'pokemon'

def read_poke_move():
    pokeId = 12
    for lang in fileLangs:
        write_to_file('../out/pokemon_' + lang + '.json', '[', 'w')
    write_to_file('../out/pokemon_stats.json', '[', 'w')
    while True:
        try:
            with open('../in/' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
                pokeData = json.loads(dataFile.read())

                # Base stats
                slot1AbilityId = convertAbilityToId(getAbility(pokeData['abilities'], 1))
                slot2AbilityId = convertAbilityToId(getAbility(pokeData['abilities'], 2))
                slot3AbilityId = convertAbilityToId(getAbility(pokeData['abilities'], 3))
                xpGain = pokeData['base_experience']
                height = pokeData['height']
                weight = pokeData['weight']
                type1Id = convertTypeToId(getType(pokeData['types'], 1))
                type2Id = convertTypeToId(getType(pokeData['types'], 2))
                speciesId = None
                special = None # Gen 1 only
                sprites = pokeData['sprites']
                
                moves = getMoves(pokeData['moves'])
                heldItems = getItems(pokeData['held_items'])
                stats = getStats(pokeData['stats'])

                pokeBaseStats = {"pokeId": pokeId, "xpGain": xpGain, "height": height, "weight": weight, "abilitySlot1": slot1AbilityId, "abilitySlot2": slot2AbilityId, "abilitySlot3": slot3AbilityId, "moves": moves, "heldItems": heldItems, "speciesId": speciesId, "sprites": sprites, "type1Id": type1Id, "type2Id": type2Id, "specialStat": special, "stats": stats}
                
                # pokeDataLangs['stats'].append(pokeBaseStats)
                # Write to stats file
                write_json_to_file('../out/' + data_type + '_stats.json', pokeBaseStats)

                # Move Localization
                for lang in fileLangs:
                    if lang == 'en':
                        name = pokeData['name']
                    else:
                        name = None
                    dataLocal = {"id": pokeId, "name": name, "description": None}
                    write_json_to_file('../out/pokemon_' + lang + '.json', dataLocal)
                    
                print('pokeId: ' + str(pokeId))
                pokeId += 1
                break
        except FileNotFoundError:
            break

    for lang in fileLangs:
        write_to_file('../out/pokemon_' + lang + '.json', ']', 'a')
    write_to_file('../out/pokemon_stats.json', ']', 'a')

def write_to_file(fileName, string, writeType):
    with open(fileName, writeType) as fileData:
        fileData.write(str(string))

def write_json_to_file(fileName, dataObj):
    with open(fileName, 'a') as jsonFile:
        json.dump(dataObj, jsonFile)

def getStats(pokeStats):
    stats = []
    for stat in pokeStats:
        genIds = [1, 2, 3, 4]
        statId = convertStatToId(stat['stat']['name'])
        ev = stat['effort']
        base = stat['base_stat']
        stats.append({'statId': statId, 'genIds': genIds, 'ev': ev, 'base': base})
    return stats

def getAbility(abilities, slot):
    for ability in abilities:
        if ability['slot'] == slot:
            return ability['ability']['name']
    return None

def getMoves(pokeMoves):
    moves = []
    for move in pokeMoves:
        moveId = convertMoveToMoveId(move['move']['name'])
        versionObjs = []
        for gameVersion in move['version_group_details']:
            learnedAtLvl = gameVersion['level_learned_at']
            moveMethodId = convertMoveMethodToId(gameVersion['move_learn_method']['name'])
            gvIds = convertGVStringToIds(gameVersion['version_group']['name'])
            isNewVersionObj = True
            # Loop through and add to final result if object does not exist, else append gvId
            for versionObj in versionObjs:
                if versionObj['learnedAtLvl'] == learnedAtLvl and versionObj['moveMethodId'] == moveMethodId:
                    versionObj['gvIds'] += gvIds
                    isNewVersionObj = False
                    break
            if isNewVersionObj:
                versionObjs.append({'learnedAtLvl': learnedAtLvl, 'moveMethodId': moveMethodId, 'gvIds': gvIds})
        moves.append({'moveId': moveId, 'versions': versionObjs})
    return moves

def convertGVStringToIds(gameVersion):
    splitOnlyOnDashWith = '[b-z0-9]-\D'
    foundBits = re.search(splitOnlyOnDashWith, gameVersion)
    if foundBits == None:
        return [convertGVStringToGVId(gameVersion)] # single game version found
    foundBits = re.split('-', foundBits[0], 1)
    gameVersions = re.split(splitOnlyOnDashWith, gameVersion, 1)
    gameVersions[0] += foundBits[0]
    gameVersions[1] = foundBits[1] + gameVersions[1]
    return [convertGVStringToGVId(gameVersions[0]), convertGVStringToGVId(gameVersions[1])]

def getItems(heldItems):
    items = []
    for item in heldItems:
        itemId = item['item']['name']
        versionObjs = []
        for gameVersion in item['version_details']:
            gvIds = [convertGVStringToGVId(gameVersion['version']['name'])]
            rarity = gameVersion['rarity']
            isNewVersionObj = True
            for versionObj in versionObjs:
                if versionObj['rarity'] == rarity:
                    versionObj['gvIds'] += gvIds
                    isNewVersionObj = False
                    break
            if isNewVersionObj:
                versionObjs.append({'gvIds': gvIds, 'rarity': rarity})
        items.append({'itemId': itemId, 'versions': versionObjs})
    return items

def getType(pokeTypes, slot):
    for type in pokeTypes:
        if type['slot'] == slot:
            return type['type']['name']

if __name__ == '__main__':
    read_poke_move()

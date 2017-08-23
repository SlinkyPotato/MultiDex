import requests
import json

from shared import *

data_type = 'pokemon'

# Berry Descriptions
pokeDataLangs['harvestStats'] = []
pokeDataLangs['descriptions'] = {}
for lang in fileLangs:
    pokeDataLangs['descriptions'][lang] = []

def read_poke_move(startIndex, lastpokeDataId):
    for pokeId in range(startIndex, lastpokeDataId):
        with open('../in/' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
            pokeData = json.loads(dataFile.read())

            # Base stats
            slot1Ability = getAbility(pokeData['abilities'], 1)
            slot2Ability = getAbility(pokeData['abilities'], 2)
            slot3Ability = getAbility(pokeData['abilities'], 3)
            xpGain = pokeData['base_experience']
            height = pokeData['height']
            weight = pokeData['weight']
            moves = getMoves(pokeData['moves'])
            heldItems = getItems(pokeData['held_items'])

            size_mm = pokeData['size']
            firmness = getObjName(pokeData['firmness'], 'name')
            natural_gift_type = getObjName(pokeData['natural_gift_type'], 'name')
            natural_gift_power = pokeData['natural_gift_power']
            item = getObjName(pokeData['item'], 'name')

            pokeBaseStats = {'berryId': pokeId, 'sizeMM': size_mm, 'firmness': firmness, 'naturalGiftType': natural_gift_type, 'naturalGiftPower': natural_gift_power, 'item': item}

            # Growth and harvest
            gen_id = 4
            max_harvest = pokeData['max_harvest']
            soil_dryness = pokeData['soil_dryness'] # gen 4 mechanics only
            growth_interval = pokeData['growth_time']
            spicy_potency = getFlavorPotency(pokeData['flavors'], 'spicy')
            dry_potency = getFlavorPotency(pokeData['flavors'], 'dry')
            sweet_potency = getFlavorPotency(pokeData['flavors'], 'sweet')
            bitter_potency = getFlavorPotency(pokeData['flavors'], 'bitter')
            sour_potency = getFlavorPotency(pokeData['flavors'], 'sour')

            pokeStats1 = {'berryId': pokeId, 'genId': gen_id, 'maxHarvest': max_harvest, 'soilDryness': soil_dryness, 'growthInterval': growth_interval, 'spicyPotency': spicy_potency, 'dryPotency': dry_potency, 'sweetPotency': sweet_potency, 'bitterPotency': bitter_potency, 'sourPotency': sour_potency}
            
            # Gen 3
            gen_id = 3
            g3_max_harvest = None
            smoothness = pokeData['smoothness']
            growth_interval = None
            spicy_potency = None
            dry_potency = None
            sweet_potency = None
            bitter_potency = None
            sour_potency = None

            pokeStats2 = {'berryId': pokeId, 'genId': gen_id, 'smoothness': smoothness, 'maxHarvest': max_harvest, 'growthInterval': growth_interval, 'spicyPotency': spicy_potency, 'dryPotency': dry_potency, 'sweetPotency': sweet_potency, 'bitterPotency': bitter_potency, 'sourPotency': sour_potency}

            pokeDataLangs['stats'].append(pokeBaseStats)
            pokeDataLangs['harvestStats'].append(pokeStats1)
            pokeDataLangs['harvestStats'].append(pokeStats2)
            
            # Move Localization
            for lang in fileLangs:
                if lang == 'en':
                    name = pokeData['name']
                else:
                    name = None
                gen_id = None
                bag_description = None
                tag_description = None
                effect = None
                dataLocal = {'id': pokeId, 'name': name, 'effect': effect}
                descLocal = {'berryId': pokeId, 'genId': gen_id, 'bagDescription': bag_description, 'tagDescription': tag_description}
                pokeDataLangs[lang].append(dataLocal)
                pokeDataLangs['descriptions'][lang].append(descLocal)
            print('pokeId: ' + str(pokeId))

    # Write to stats file
    with open('../out/' + data_type + '_stats.json', 'w') as stats:
        json.dump(pokeDataLangs['stats'], stats)

    with open('../out/' + data_type + '_harvest_stats.json', 'w') as harvestStats:
        json.dump(pokeDataLangs['harvestStats'], harvestStats)

    # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/berries_' + lang + '.json', 'w') as langFile:
            json.dump(pokeDataLangs[lang], langFile)
        with open('../out/berry_descriptions_' + lang + '.json', 'w') as descFile:
            json.dump(pokeDataLangs['descriptions'][lang], descFile)

def getAbility(abilities, slot):
    for ability in abilities:
        if ability['slot'] == slot:
            return ability['ability']['name']
    return None

def getMoves(pokeMoves):
    moves = []
    for move in pokeMoves:
        moveId = move['move']['name']
        genId = move['version_group_details']['version_group']['name']
        learnedAtLvl = move['level_learn_at']
        moveMethodId = move['move_learn_method']['name']
        moves.append({'moveId': moveId, 'genId': genId, 'learnedAtLvl': learnedAtLvl, 'moveMethodId': moveMethodId})
    return moves

def getItems(heldItems):
    items = []
    for item in heldItems:
        itemName = item['item']['name']
        gameVersionId = item['version_details']['version']['name']
        rarity = item['version_details']['rarity']
        items.append({'itemId': itemName, 'gvId': gameVersionid, 'rarity': rarity})

def getFlavorPotency(flavors, flavor):
    for flavorObj in flavors:
        if flavorObj['flavor'] == None or flavorObj['flavor']['name'] == None:
            continue
        if flavorObj['flavor']['name'] == flavor:
            return flavorObj['potency']
    return None

if __name__ == '__main__':
    read_poke_move(1, 65)

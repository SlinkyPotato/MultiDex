import requests
import json

from shared import *

data_type = 'berry'

# Berry Descriptions
pokeDataLangs['harvestStats'] = []
pokeDataLangs['descriptions'] = {}
for lang in fileLangs:
    pokeDataLangs['descriptions'][lang] = []

def read_poke_move(startIndex, lastpokeDataId):
    for pokeId in range(startIndex, lastpokeDataId):
        with open('../in/poke_' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
            pokeData = json.loads(dataFile.read())

            # Base stats
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
                    name = pokeData['name'] + '-berry'
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

def getFlavorPotency(flavors, flavor):
    for flavorObj in flavors:
        if flavorObj['flavor'] == None or flavorObj['flavor']['name'] == None:
            continue
        if flavorObj['flavor']['name'] == flavor:
            return flavorObj['potency']
    return None

if __name__ == '__main__':
    read_poke_move(1, 65)

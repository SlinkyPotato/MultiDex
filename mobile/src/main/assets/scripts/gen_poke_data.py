import requests
import json

from shared import *

data_type = 'berry'

def read_poke_move(startIndex, lastpokeDataId):
    for pokeId in range(startIndex, lastpokeDataId):
        with open('../in/poke_' + data_type + '_' + str(pokeId) + '.json', 'r') as dataFile:
            pokeData = json.loads(dataFile.read())

            # Base stats
            sizeMM = pokeData['size']
            firmness = getObjName(pokeData['firmness'], 'name')
            natural_gift_type = getObjName(pokeData['natural_gift_type'], 'name')
            natural_gift_power = pokeData['natural_gift_power']
            item = getObjName(pokeData['item'], 'name')

            # Growth and harvest
            genId = 4
            max_harvest = pokeData['max_harvest']
            soil_dryness = pokeData['soil_dryness'] # gen 4 mechanics only
            growthInterval = pokeData['growth_time']
            spicyPotency = getFlavorPotency(pokeData['flavors'], 'spicy')
            dryPotency = getFlavorPotency(pokeData['flavors'], 'dry')
            sweetPotency = getFlavorPotency(pokeData['flavors'], 'sweet')
            bitterPotency = getFlavorPotency(pokeData['flavors'], 'bitter')
            sourPotency = getFlavorPotency(pokeData['flavors'], 'sour')
            
            # Gen 3
            g3_max_harvest = None
            smoothness = pokeData['smoothness']
            g3_growthInterval = None
            g3_spicyPotency = None
            g3_dryPotency = None
            g3_sweetPotency = None
            g3_bitterPotency = None
            g3_sourPotency = None

            pokeStats = {'berryId': pokeId, 'naturalGiftPower': natural_gift_power, 'naturalGiftType': natural_gift_type, 'maxHarvest': max_harvest, 'soilDryness': soil_dryness, 'smoothness': smoothness, 'growthInterval': growthInterval, 'item': item, 'firmness': firmness, 'sizeMM': sizeMM, 'genId': genId}
            pokeDataLangs['stats'].append(pokeStats)
            
            # Move Localization
            for lang in fileLangs:
                name = getLocalText(lang, 'name', pokeData['names'])
                description = None
                effect = None
                dataLocal = {'id': pokeId, 'name': name}
                pokeDataLangs[lang].append(dataLocal)
            print('pokeId: ' + str(pokeId))

    # Write to stats file
    with open('../out/' + data_type + '_stats.json.json', 'w') as moveStats:
        json.dump(pokeMovesLangs['stats'], moveStats)

    # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/berries_' + lang + '.json', 'w') as langFile:
            json.dump(pokeDataLangs[lang], langFile)

if __name__ == '__main__':
    read_poke_move(1, 3)


def getFlavorPotency(falvors, flavor):
    for flavor in flavors:
        if flavor['flavor'] == None or flavor['flavor']['name'] == None:
            continue
        if flavor['flavor']['name'] == falvor:
            return flavor['flavor']['potency']
    return None

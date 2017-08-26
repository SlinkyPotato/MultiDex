import json

from shared import *

data_type = 'item'
emptyIndexes = [667, 672, 680]

def read_poke_move():
    pokeId = 1
    bump = 0
    while True:
        if (pokeId + bump) in emptyIndexes:
            bump += 1
        try:
            with open('../in/' + data_type + '_' + str(pokeId + bump) + '.json', 'r') as dataFile:
                pokeData = json.loads(dataFile.read())

                # Base stats
                cost = pokeData['cost']
                flingPower = pokeData['fling_power']
                itemAttributes = getAttributes(pokeData['attributes'])
                itemCategory = pokeData['category']['name']
                sprites = pokeData['sprites']

                pokeBaseStats = {"itemId": pokeId, "cost": cost, "flingPower": flingPower, "itemAttributeIds": itemAttributes, "itemCategoryId": itemCategory, "sprites": sprites}
                pokeDataLangs['stats'].append(pokeBaseStats)

                # Move Localization
                for lang in fileLangs:
                    # name = pokeData['name']
                    name = getLocalText(lang, 'name', pokeData['names'])
                    effect = getLocalText(lang, 'short_effect', pokeData['effect_entries'])
                    descriptions = getDescriptions(pokeData['flavor_text_entries'], lang)
                    dataLocal = {"id": pokeId, "name": name, "descriptions": descriptions}
                    pokeDataLangs[lang].append(dataLocal)
                    
                print('pokeId: ' + str(pokeId))
                pokeId += 1
                # break
        except FileNotFoundError:
            break

    # Write to stats file
    with open('../out/' + data_type + '_stats.json', 'w') as stats:
        json.dump(pokeDataLangs['stats'], stats)

    # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/'+ data_type +'s_' + lang + '.json', 'w') as langFile:
            json.dump(pokeDataLangs[lang], langFile)

def getAttributes(pokeAttributes):
    attributes = []
    for attribute in pokeAttributes:
        attrId = convertItemAttrToId(attribute['name'])
        attributes.append(attrId)
    return attributes

def getDescriptions(pokeDescriptions, lang):
    resultDescriptions = []
    for flavorText in pokeDescriptions:
        if lang != flavorText['language']['name']:
            continue
        description = flavorText['text']
        gvIds = convertGVStringToIds(flavorText['version_group']['name'])
        isNewVersionObj = True
        for resultDescription in resultDescriptions:
            if resultDescription['description'] == description:
                resultDescription['gvIds'] += gvIds
                isNewVersionObj = False
                break
        if isNewVersionObj:
            resultDescriptions.append({'gvIds': gvIds, 'description': description})
    return resultDescriptions

if __name__ == '__main__':
    read_poke_move()

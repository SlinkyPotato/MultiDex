import requests
import json

baseUrl = 'https://pokeapi.co/api/v2/'

fileLangs = ['en', 'es', 'de', 'fr', 'it', 'ja', 'ko']
gens = {'generation-i': 1, 'generation-ii': 2, 'generation-iii': 3, 'generation-iv': 4, 'generation-v': 5, 'generation-vi': 6}
pokeTypes = {'normal': 1, 'fire': 2, 'water': 3, 'electric': 4, 'grass': 5, 'ice': 6, 'fighting': 7, 'poison': 8, 'ground': 9, 'flying': 10, 'psychic': 11, 'bug': 12, 'rock': 13, 'ghost': 14, 'dragon': 15, 'dark': 16, 'steel': 17, 'fairy': 18, '???': 19}

def request_poke_api(startIndex, lastPokeMoveId):
    for typeId in range(startIndex, lastPokeMoveId):
        # Call the poke moves request
        pokeType = requests.get(baseUrl + 'name/' + str(typeId)).json()
        with open('../in/poke_type_' + str(typeId) + '.json', 'x') as pokeTypeFile:
            json.dump(pokeType, pokeTypeFile)
            print('poke_type_' + str(typeId) + '.json')

def read_type_file(startIndex, lastPokeMoveId):
    finalList = {'stats': [], 'en': [], 'es': [], 'de': [], 'fr': [], 'it': [], 'ja': [], 'ko': []}
    for pokeTypeId in range(startIndex, lastPokeMoveId):
        with open('../in/poke_type_' + str(pokeTypeId) + '.json', 'r') as moveFile:
            pokeType = json.loads(moveFile.read())
            # Stats
            genId = getListText(gens, pokeType['generation'])
            damageRelations = pokeType['damage_relations']
            doubleDmg = extractArray(damageRelations['double_damage_to'])
            halfDmg = extractArray(damageRelations['half_damage_to'])
            noDmg = extractArray(damageRelations['no_damage_to'])
            normalDmg = extractNormalDmg(doubleDmg, halfDmg, noDmg)

            pokeStatsObj = {'pokeTypeId': pokeTypeId, 'genId': genId, 'doubleDmg': doubleDmg, 'halfDmg': halfDmg, 'noDmg': noDmg, 'normalDmg': normalDmg}
            finalList['stats'].append(pokeStatsObj)
            
            # Move Localization
            for lang in fileLangs:
                name = getLocalText(lang, 'name', pokeType['names'])
                langObj = {'id': pokeTypeId, 'name': name, 'description': None, 'notes': None}
                finalList[lang].append(langObj)
            print('pokeTypeId: ' + str(pokeTypeId))
            
    # Write to stats file
    with open('../out/poke_type_stats.json', 'w') as pokeStats:
        json.dump(finalList['stats'], pokeStats)

    # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/poke_types_' + lang + '.json', 'w') as langFile:
            json.dump(finalList[lang], langFile)

def getListText(customList, contestName):
    if (contestName == None):
        return None
    contestName = contestName['name']
    if (contestName == None):
        return None
    contestName.lower()
    return customList[contestName]

def getLocalText(lang, textType, listOfTexts):
    for text in listOfTexts:
        if (text['language']['name'][:2].lower() == lang):
            return text[textType]

def extractArray(typesList):
    convertedList = []
    for type in typesList:
        convertedList.append(pokeTypes[type['name']])
    return convertedList

def extractNormalDmg(doubleDmg, halfDmg, noDmg):
    doubleDmg = set(doubleDmg)
    halfDmg = set(halfDmg)
    noDmg = set(noDmg)
    allDmgs = set.union(doubleDmg, halfDmg, noDmg)
    allTypes = set(range(1, 19))
    return list(allTypes - allDmgs)

if __name__ == '__main__':
    # request_poke_api(1, 19)
    read_type_file(1, 19)

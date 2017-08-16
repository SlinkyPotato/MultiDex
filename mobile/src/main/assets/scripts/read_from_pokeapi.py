import requests
import json

baseUrl = 'https://pokeapi.co/api/v2/'
fileLangs = ['en', 'es', 'de', 'fr', 'it', 'ja', 'ko']
gens = {'genId-i': 1, 'genId-ii': 2, 'genId-iii': 3, 'genId-iv': 4, 'genId-v': 5, 'genId-vi': 6}
pokeTypes = {'normal': 1, 'fire': 2, 'water': 3, 'electric': 4, 'grass': 5, 'ice': 6, 'fighting': 7, 'poison': 8, 'ground': 9, 'flying': 10, 'psychic': 11, 'bug': 12, 'rock': 13, 'ghost': 14, 'dragon': 15, 'dark': 16, 'steel': 17, 'fairy': 18, '???': 19}
dmgTypes = {'physical': 1, 'special': 2, 'status': 3}
contestTypes = {'cool': 1, 'beautiful': 2, 'beauty': 2, 'cute': 3, 'clever': 4, 'smart': 4, 'tough': 5}

def request_poke_api(startIndex, lastPokeMoveId):
    for pokeId in range(startIndex, lastPokeMoveId):
        # Call the poke moves request
        pokeMove = requests.get('https://pokeapi.co/api/v2/move/' + str(pokeId)).json()
        with open('../in/poke_move_' + str(pokeId) + '.json', 'x') as pokeMoveFile:
            json.dump(pokeMove, pokeMoveFile)
            print('poke_move_' + str(pokeId) + '.json')

def read_poke_move(startIndex, lastPokeMoveId):
    pokeMovesLangs = {'stats': [], 'en': [], 'es': [], 'de': [], 'fr': [], 'it': [], 'ja': [], 'ko': []}
    for pokeId in range(startIndex, lastPokeMoveId):
        with open('../in/poke_move_' + str(pokeId) + '.json', 'r') as moveFile:
            pokeMove = json.loads(moveFile.read())
            # Stats
            accuracy = pokeMove['accuracy']
            contestTypeId = getListText(contestTypes, pokeMove['contest_type'])
            dmgTypeId = getListText(dmgTypes, pokeMove['damage_class'])
            genId = getListText(gens, pokeMove['genId'])
            pp = pokeMove['pp']
            pokeTypeId = getListText(pokeTypes, pokeMove['type'])
            power = pokeMove['power']

            moveStats = {'pokeMoveId': pokeId, 'pokeTypeId': pokeTypeId, 'damageTypeId': dmgTypeId, 'contestTypeId': contestTypeId, 'powerPoint': pp, 'power': power, 'accuracy': accuracy, 'genId': genId}
            pokeMovesLangs['stats'].append(moveStats)
            
            # Move Localization
            for lang in fileLangs:
                name = getLocalText(lang, 'name', pokeMove['names'])
                description = getLocalText(lang, 'flavor_text', pokeMove['flavor_text_entries'])
                effect = getLocalText(lang, 'effect', pokeMove['effect_entries'])
                moveLocal = {'id': pokeId, 'name': name, 'description': description, 'effect': effect, 'notes': None}
                pokeMovesLangs[lang].append(moveLocal)
            print('pokeId: ' + str(pokeId))
            
    # Write to stats file
    with open('../out/poke_move_stats.json.json', 'w') as moveStats:
        json.dump(pokeMovesLangs['stats'], moveStats)

    # Write to each poke moves lang
    for lang in fileLangs:
        with open('../out/poke_moves_' + lang + '.json', 'w') as langFile:
            json.dump(pokeMovesLangs[lang], langFile)

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
    return None

if __name__ == '__main__':
    # request_poke_api(623, 622)
    read_poke_move(1, 622)

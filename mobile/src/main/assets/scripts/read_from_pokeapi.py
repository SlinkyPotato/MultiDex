import requests
import json

baseUrl = 'https://pokeapi.co/api/v2/'
fileLangs = ['en', 'es', 'de', 'fr', 'it', 'ja', 'ko']
gens = {'generation-i': 1, 'generation-ii': 2, 'generation-iii': 3, 'generation-iv': 4, 'generation-v': 5, 'generation-vi': 6}
pokeTypes = {'normal': 0, 'fire': 1, 'water': 2, 'electric': 3, 'grass': 4, 'ice': 5, 'fighting': 6, 'poison': 7, 'ground': 8, 'flying': 9, 'psychic': 10, 'bug': 11, 'rock': 12, 'ghost': 13, 'dragon': 14, 'dark': 15, 'steel': 16, 'fairy': 17, '???': 18}
dmgTypes = {'physical': 0, 'special': 1, 'status': 2}
contestTypes = {'cool': 0, 'beautiful': 1, 'beauty': 1, 'cute': 2, 'clever': 3, 'smart': 3, 'tough': 4}

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
            genId = getListText(gens, pokeMove['generation'])
            pp = pokeMove['pp']
            pokeTypeId = getListText(pokeTypes, pokeMove['type'])
            power = pokeMove['power']

            moveStats = {'id': pokeId, 'pokeTypeId': pokeTypeId, 'dmgTypeId': dmgTypeId, 'contestTypeId': contestTypeId, 'powerPoint': pp, 'power': power, 'accuracy': accuracy, 'genId': genId}
            pokeMovesLangs['stats'].append(moveStats)
            
            # Move Localization
            description = pokeMove['flavor_text_entries']
            effect = pokeMove['effect_entries']
            
            for lang in fileLangs:
                name = getLocalText(lang, 'name', pokeMove['names'])
                description = getLocalText(lang, 'flavor_text', pokeMove['flavor_text_entries'])
                effect = getLocalText(lang, 'effect', pokeMove['effect_entries'])
                moveLocal = {'id': pokeId, 'name': name, 'description': description, 'effect': effect, 'notes': None}
                pokeMovesLangs[lang].append(moveLocal)
            print('pokeId: ' + str(pokeId))
            
    # Write to stats file
    with open('../out/move_stats.json', 'w') as moveStats:
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

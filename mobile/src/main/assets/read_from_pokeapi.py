import requests
import json

baseUrl = 'https://pokeapi.co/api/v2/'
fileLangs = ['en', 'es', 'de', 'fr', 'it', 'ja', 'ko']
gens = {'generation-i': 1, 'generation-ii': 2, 'generation-iii': 3, 'generation-iv': 4, 'generation-v': 5, 'generation-vi': 6}
pokeTypes = {'normal': 0, 'fire': 1, 'water': 2, 'electric': 3, 'grass': 4, 'ice': 5, 'fighting': 6, 'poison': 7, 'ground': 8, 'flying': 9, 'psychic': 10, 'bug': 11, 'rock': 12, 'ghost': 13, 'dragon': 14, 'dark': 15, 'steel': 16, 'fairy': 17, '???': 18}
dmgTypes = {'physical': 0, 'special': 1, 'status': 2}
contestTypes = {'cool': 0, 'beautiful': 1, 'cute': 2, 'clever': 3, 'tough': 4}

def request_poke_api(startIndex, lastPokeMoveId):
    pokeMovesLangs = {'stats': [], 'en': [], 'es': [], 'de': [], 'fr': [], 'it': [], 'ja': [], 'ko': []}
    for pokeId in range(startIndex, 2):
        # Call the poke moves request
        pokeMove = requests.get('https://pokeapi.co/api/v2/move/' + pokeId).json()

        with open('in/poke_move_' + pokeId, 'w') as pokeMoveFile:
            json.dump(pokeMove, pokeMoveFile)

def read_poke_move(startIndex, lastPokeMoveId):
    for pokeId in range(startIndex, lastPokeMoveId):
        with open('poke_move_' + pokeId, 'r') as pokeMove:
            # Stats
            accuracy = pokeMove['accuracy']
            contestTypeId = contestTypes[pokeMove['contest_type']['name'].lower()]
            dmgTypeId = dmgTypes[pokeMove['damage_class']['name'].lower()]
            genId = gens[pokeMove['generation']['name'].lower()]
            pp = pokeMove['pp']
            pokeTypeId = pokeTypes[pokeMove['type']['name'].lower()]
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
                moveLocal = {'id': pokeId, 'name': name, 'description': description, 'effect': effect}
                pokeMovesLangs[lang].append(moveLocal)
            
    # Write to each poke moves lang
    for lang in fileLangs:
        with open('out/poke_moves_' + lang, 'w') as langFile:
            json.dump(pokeMovesLangs.lang, langFile)

def getLocalText(lang, textType, listOfTexts):
    for text in listOfTexts:
        if (text['language']['name'][:2].lower() == lang):
            return text[textType]
    return None

# def getEffect(lang, effects):
#     for effect in effects:
#         if (effect['language'])

# def getName(lang, names):
#     for name in names:
#         if (name['language']['name'][:2] == lang):
#             return name['name']
#     return None

# def getDescription(lang, flavorTexts):
#     for flavorText in flavorTexts:
#         if (flavorText['language']['name'][:2] == lang):
#             return flavorText['flavor_text']
#     return None

# def getGeneration(genName):
#     gens = ['i', 'ii', 'iii', 'iv', 'v', 'vi']
#     for genId, v in enumerate(gens):
#         if (genName == 'generation-' + v):
#             return genId
#     return None

# def getPokeTypeId(typeName):
#     pokeTypes = ['Normal', 'Fire', 'Water', 'Electric', 'Grass', 'Ice', 'Fighting', 'Poison', 'Ground', 'Flying', 'Psychic', 'Bug', 'Rock', 'Ghost', 'Dragon', 'Dark', 'Steel', 'Fairy', '???']
#     for pokeTypeId, v in enumerate(pokeTypes):
#         if (typeName == v):
#             return pokeTypeId
#     return None

# def getDmgType(dmgName):
#     dmgTypes = ['Physical', 'Special', 'Status']
#     for dmgTypeId, v in enumerate(dmgTyps):
#         if (dmgName == v):
#             return dmgTypeId
#     return None

# def getContestType(contestName):
#     contestTypes = ['Cool', 'Beautiful', 'Cute', 'Clever', 'Tought']
#     for contestTypeId, v in enumerate(contestTypes):
#         if (contestName == v):
#             return contestTypeId

if __name__ == '__main__':
    request_poke_api()
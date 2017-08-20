baseUrl = 'http://localhost:8000/api/v2/'

fileLangs = ['en', 'es', 'de', 'fr', 'it', 'ja', 'ko', 'zh', 'cs']
gens = {'genId-i': 1, 'genId-ii': 2, 'genId-iii': 3, 'genId-iv': 4, 'genId-v': 5, 'genId-vi': 6}
pokeTypes = {'normal': 1, 'fire': 2, 'water': 3, 'electric': 4, 'grass': 5, 'ice': 6, 'fighting': 7, 'poison': 8, 'ground': 9, 'flying': 10, 'psychic': 11, 'bug': 12, 'rock': 13, 'ghost': 14, 'dragon': 15, 'dark': 16, 'steel': 17, 'fairy': 18, '???': 19}
dmgTypes = {'physical': 1, 'special': 2, 'status': 3}
contestTypes = {'cool': 1, 'beautiful': 2, 'beauty': 2, 'cute': 3, 'clever': 4, 'smart': 4, 'tough': 5}

# Gen pokeData Langs
pokeDataLangs = {'stats': []}
for lang in fileLangs:
    pokeDataLangs[lang] = []

def getLocalText(lang, textType, listOfTexts):
    for text in listOfTexts:
        if (text['language']['name'][:2].lower() == lang):
            return text[textType]
    return None

def getObjName(customObj, fieldName):
    if (customObj == None):
        return None
    dataField = customObj[fieldName]
    if (dataField == None):
        return None
    return dataField.lower()

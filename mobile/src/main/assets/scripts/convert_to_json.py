## Read from a txt file and convert it to json format

import json

file_name = '../in/moves.txt'
json_file = '../out/moves.json'
starting_move_id = 468
ending_move_id = 621

contest_types = {'Cute': 3, 'Tough': 5, 'Clever': 4, 'Beautiful': 2, 'Cool': 1}

converted_data = []

with open(file_name, 'r') as readFile:
    for line in readFile:
        id = ''
        name = ''
        contest_type = ''
        v_index = 0 # variable index to specify id, name or contest_types
        was_already_bumped = False
        for char in line:
            if char.isdecimal() and v_index == 0:
                id = id + char
                was_already_bumped = False
            elif char.isalpha() and v_index == 1:
                name = name + char
                was_already_bumped = False
            elif char.isalpha() and v_index == 2:
                contest_type = contest_type + char
                was_already_bumped = False
            elif not was_already_bumped:
                v_index += 1
                was_already_bumped = True

        contest_type_id = contest_types[contest_type]
        # contest_type_id = 0
        converted_data.append({'pokeMoveId': int(id), 'name': name, 'contestTypeId': contest_type_id, 'contestTypeName': contest_type})

def is_space_tab(char):
    if char == '' or char == ' ' or ord(char) == 9:
        return True
    else:
        return False

# Write generated array of dicts to file
with open(json_file, 'w') as writeFile:
    json.dump(converted_data, writeFile)
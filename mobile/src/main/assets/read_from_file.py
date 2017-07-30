def read_poke_moves():
    readFile = open('poke_moves.txt', 'r')
    writeFile = open('write.txt', 'w')

    lineNum = 0

    while (readFile):

        if lineNum == 719:
            break
        
        line = readFile.readline()

        hasTwoSpaces = False
        isPreviousLetter = False

        for char in line:
            if hasTwoSpaces and char.isalpha():
                hasTwoSpaces = False
                
            if not isPreviousLetter and char.isalpha():
                isPreviousLetter = True

            if (hasTwoSpaces and char == ' ') or (isPreviousLetter and ord(char) == 9):
                break

            if not hasTwoSpaces and char == ' ':
                hasTwoSpaces = True
            
            if char == '\n':
                break

            writeFile.write(char)

        writeFile.write('\n')
        lineNum += 1

    readFile.close()
    writeFile.close()

if __name__ == '__main__':
    request_poke_api()

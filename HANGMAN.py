#below of that is the code give with using libabry of python...
import random
import sqlite3
from prettytable import PrettyTable


#Youhave to install this command pip install prettytable then you can see this output..

# Hangman for this code....
HANGMAN_PICS = ['''
  +---+
      |
      |
      |
     ===''', '''
  +---+
  O   |
      |
      |
     ===''', '''
  +---+
  O   |
  |   |
      |
     ===''', '''
  +---+
  O   |
 /|   |
      |
     ===''', '''
  +---+
  O   |
 /|\  |
      |
     ===''', '''
  +---+
  O   |
 /|\  |
 /    |
     ===''', '''
  +---+
  O   |
 /|\  |
 / \  |
     ===''', '''
  +---+
 [O   |
 /|\  |
 / \  |
     ===''', '''
  +---+
 [O]  |
 /|\  |
 / \  |
     ===''']

# Word set of the lits is below..
word_sets = {
    'Animal': 'buffelow ant baboon bat bear beaver camel dog cat clam cobra'.split(),
    'Shape': 'square round  rectangle triangle  circle ellipse rhombus trapezoid'.split(),
    'Place': 'usa india  London Paris  Istanbul Riyadh'.split()
}

# Database of that is setup is below...
conn = sqlite3.connect('hangman.db')
c = conn.cursor()
c.execute('''
CREATE TABLE IF NOT EXISTS hall_of_fame (
    id INTEGER PRIMARY KEY,
    player_name TEXT,
    level TEXT,
    remaining_lives INTEGER
)''')
conn.commit()

def getRandomWord(wordList):
    return random.choice(wordList)

def displayBoard(missedLetters, correctLetters, secretWord, remainingLives):
    print(HANGMAN_PICS[len(missedLetters)])
    print(f'Remaining lives: {remainingLives}')
    print('Missed letters:', ' '.join(missedLetters))
    
    blanks = '_' * len(secretWord)
    for i in range(len(secretWord)):
        if secretWord[i] in correctLetters:
            blanks = blanks[:i] + secretWord[i] + blanks[i+1:]

    for letter in blanks:
        print(letter, end=' ')
    print()

def getGuess(alreadyGuessed):
    while True:
        print('Guess a letter.')
        guess = input().lower()
        if len(guess) != 1:
            print('Please enter a single letter.')
        elif guess in alreadyGuessed:
            print('You have already guessed that letter. Choose again.')
        elif guess not in 'abcdefghijklmnopqrstuvwxyz':
            print('Please enter a LETTER.')
        else:
            return guess

def playAgain():
    print('Do you want to play again? (yes or no)')
    return input().lower().startswith('y')

def selectLevel():
    table = PrettyTable()
    table.field_names = ["Option", "Level"]
    table.add_row(["1", "Easy"])
    table.add_row(["2", "Moderate"])
    table.add_row(["3", "Hard"])
    print(table)
    while True:
        choice = input('Select the level (1, 2, or 3): ')
        if choice in '123':
            return int(choice)
        else:
            print('Invalid choice, please select 1, 2, or 3.')

def selectWordSet():
    table = PrettyTable()
    table.field_names = ["Option", "Word Set"]
    table.add_row(["1", "Animals"])
    table.add_row(["2", "Shapes"])
    table.add_row(["3", "Places"])
    print(table)
    while True:
        choice = input('Select the word set (1, 2, or 3): ')
        if choice in '123':
            sets = ['Animal', 'Shape', 'Place']
            return sets[int(choice) - 1]
        else:
            print('Invalid choice, please select 1, 2, or 3.')

def displayHallOfFame():
    c.execute('SELECT level, player_name, remaining_lives FROM hall_of_fame')
    results = c.fetchall()
    
    table = PrettyTable()
    table.field_names = ["Level", "Winner Name", "Remaining Lives"]
    for row in results:
        table.add_row(row)
    print(table)

def aboutGame():
    print("ABOUT THE GAME")
    print('''
Easy: You can select the word setof that the list of  (Animal, Shape, Place)... You have to  8 of  lives...
Moderate: You can select the word set, but you have only  of the 6 lives...
Hard: The word set is randomly selected, and you have only  of that 6 lives.
''')

def updateHallOfFame(player_name, level, remaining_lives):
    c.execute('SELECT remaining_lives FROM hall_of_fame WHERE level = ? ORDER BY remaining_lives DESC LIMIT 1', (level,))
    row = c.fetchone()
    if row is None or remaining_lives > row[0]:
        c.execute('INSERT INTO hall_of_fame (player_name, level, remaining_lives) VALUES (?, ?, ?)', (player_name, level, remaining_lives))
        conn.commit()

print('H A N G M A N')
player_name = input("Please enter your name: ")
gameIsDone = False

while True:
    print(f"Hi {player_name}.\nWelcome to HANGMAN")
    table = PrettyTable()
    table.field_names = ["Option", "Menu"]
    table.add_row(["1", "PLAY THE GAME"])
    table.add_row(["2", "Hall of Fame"])
    table.add_row(["3", "About the Game"])
    print(table)
    
    choice = input("Choose an option: ")
    if choice == '1':
        level = selectLevel()
        if level == 1:
            word_set = selectWordSet()
            secretWord = getRandomWord(word_sets[word_set])
            remainingLives = 8
        elif level == 2:
            word_set = selectWordSet()
            secretWord = getRandomWord(word_sets[word_set])
            remainingLives = 6
        elif level == 3:
            word_set = random.choice(list(word_sets.keys()))
            secretWord = getRandomWord(word_sets[word_set])
            remainingLives = 6
        
        missedLetters = ''
        correctLetters = ''
        
        while True:
            displayBoard(missedLetters, correctLetters, secretWord, remainingLives - len(missedLetters))
            guess = getGuess(missedLetters + correctLetters)
            
            if guess in secretWord:
                correctLetters += guess
                if all(letter in correctLetters for letter in secretWord):
                    print(f'Yes! The secret word is "{secretWord}"! You have won!')
                    gameIsDone = True
                    updateHallOfFame(player_name, ["Easy", "Moderate", "Hard"][level-1], remainingLives - len(missedLetters))
                    break
            else:
                missedLetters += guess
                if len(missedLetters) == remainingLives:
                    displayBoard(missedLetters, correctLetters, secretWord, remainingLives - len(missedLetters))
                    print(f'You have run out of guesses!\nAfter {len(missedLetters)} missed guesses and {len(correctLetters)} correct guesses, the word was "{secretWord}"')
                    gameIsDone = True
                    break

    elif choice == '2':
        displayHallOfFame()
    elif choice == '3':
        aboutGame()
    else:
        print("Invalid choice, please select 1, 2, or 3.")
    
    if gameIsDone:
        if playAgain():
            gameIsDone = False
        else:
            break

conn.close()

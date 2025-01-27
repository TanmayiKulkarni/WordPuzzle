import java.io.StringReader;
import java.util.*;
class BoggleSolver5 {
private static String ANSI_RESET = "\u001B[0m";
private static String ANSI_RED = "\u001B[31m";
private static String ANSI_GREEN = "\u001B[32m";
private static String ANSI_BLUE = "\u001B[34m";
String word;
int score = 0; 
boolean timerUp;
private static final Random random = new Random();
Dictionary dictionary = new Dictionary();
int level;
//global declarations
Scanner in = new Scanner(System.in);
char[][] grid; //grid for generation
char[][] outputGrid;//output grid
boolean flag;
Stack<String> useranswers = new Stack<>();
Stack<Boolean> printable = new Stack<>();
ArrayList<String> words = new ArrayList<>();
HashSet<String> output = new HashSet<>(); //to avoid duplicates
HashSet<String> check2 = new HashSet<>();
String arr[]=new String[output.size()];
//select random words from dictionary to generate grid
ArrayList<String> createArrayList() {
dictionary.insert();
words.clear();
output.clear();
check2.clear();
int no_of_words = 0;
switch (level) {
case 1: no_of_words = 5; break;
case 2: no_of_words = 7; break;
case 3: no_of_words = 10; break;
}
for (int i = 0; i < no_of_words; i++) {
int randomInt1 = random.nextInt(40);
int a = dictionary.Hashtable[randomInt1].size();
if (a > 0) {
int randomInt2 = random.nextInt(a);
words.add(dictionary.Hashtable[randomInt1].get(randomInt2));
}
}
return words;
}
//creating a grid to solve
char[][] createBoggleGrid(int size, List<String> words) {
char[][] grid = new char[size][size];
for (int i = 0; i < size; i++) {
for (int j = 0; j < size; j++) {
grid[i][j] = '-';
}
}
for (String word : words) {
placeWordInGrid(grid, word.toUpperCase());
}
fillRandomLetters(grid);
return grid;
}
//this function places words at random in the grids by checking if they can be placed at that position
void placeWordInGrid(char[][] grid, String word) {
int size = grid.length;
boolean placed = false;
while (!placed) {
int row = random.nextInt(size);
int col = random.nextInt(size);
boolean horizontal = random.nextBoolean();
if (canPlaceWord(grid, word, row, col, horizontal)) { //function call to method which checks whether the word can be placed at random location
for (int i = 0; i < word.length(); i++) {
if (horizontal) {
grid[row][col + i] = word.charAt(i);
} else {
grid[row + i][col] = word.charAt(i);
}
}
placed = true;
}
}
}
boolean canPlaceWord(char[][] grid, String word, int row, int col, boolean horizontal) {
int size = grid.length;
for (int i = 0; i < word.length(); i++) {
if (horizontal) {
if (col + i >= size || grid[row][col + i] != '-') {
return false;
}
} else {
if (row + i >= size || grid[row + i][col] != '-') {
return false;
}
}
// here we check whether the word can be placed it checks that the row index+ word length one by one is less than size similar fir columns
}
return true;
}
void fillRandomLetters(char[][] grid) { // This method places a random character where ever it finds an empty space
for (int i = 0; i < grid.length; i++) {
for (int j = 0; j < grid[i].length; j++) {
if (grid[i][j] == '-') {
grid[i][j] = (char) ('A' + random.nextInt(26));
}
}
}
}
void printGrid(char[][] grid) {
for (char[] row : grid) {
for (char cell : row) {
System.out.print(cell + " ");
}
System.out.println();
}
}
void create(int size) {
if (size == 10) {
level = 1;
} else if (size == 13) {
level = 2;
} else if (size == 15) {
level = 3;
} else {
return;
}
createArrayList();
grid = createBoggleGrid(size, words);
printGrid(grid);
}
// Checking if first alphabet of our words matches with the alphabet in the grid, so that it checks horizontal and vertical only if it matches
boolean searchWord(String word) {
int size = grid.length;
String uppercaseWord = word.toUpperCase();
for (int row = 0; row < size; row++) {
for (int col = 0; col < size; col++) {
if (grid[row][col] == uppercaseWord.charAt(0)) {
if (checkHorizontal(grid, uppercaseWord, row, col, outputGrid) || checkVertical(grid, uppercaseWord, row, col, outputGrid)) {
return true;
}
}
}
}
return false;
}
//check if the word fits horizontally by checking if the word length+column no. is equal to or smaller than size. If it doesn't fit, it returns false.
//where the alphabet matches, it compares character by character horizontally in the grid, with the alphabets in our word.
//if any character doesn't match, it returns false
boolean checkHorizontal(char[][] grid, String word, int row, int col, char[][] outputGrid) {
int size = grid.length;
if (col + word.length() > size) {
return false;
}
for (int i = 0; i < word.length(); i++) {
if (grid[row][col + i] != word.charAt(i)) {
return false;
}
}
for (int i = 0; i < word.length(); i++) {
outputGrid[row][col + i] = word.charAt(i);
}
output.add(word + " From (" + row + "," + col + ") to (" + row + "," + (col + word.length() - 1) + ")");
return true;
//If the word is found, we its characters in respective cells in output grid
//We add the word found to output arraylist and display its start and end cell.
}
//check if the word fits vertically by checking if the word length+row no. is equal to or smaller than size. If it doesn't fit, it returns false
//where the alphabet matches, it compares character by character vertically in the grid, with the alphabets in our word.
//if any character doesn't match, it returns false
boolean checkVertical(char[][] grid, String word, int row, int col, char[][] outputGrid) {
int size = grid.length;
if (row + word.length() > size) {
return false;
}
for (int i = 0; i < word.length(); i++) {
if (grid[row + i][col] != word.charAt(i)) {
return false;
}
}
for (int i = 0; i < word.length(); i++) {
outputGrid[row + i][col] = word.charAt(i);
}
output.add(word + " From (" + row + "," + col + ") to (" + (row + word.length() - 1) + "," + col + ")");
return true;
//If the word is found, we its characters in respective cells in output grid
//We add the word found to output arraylist and display its start and end cell.
}
void printOutputGrid() {
for (char[] row : outputGrid) {
for (char cell : row) {
System.out.print(cell + " ");
}
System.out.println();
}
for (String i : output) {
System.out.println(i);
}
}
//printing output grid
void search(int size) {
boolean check;
outputGrid = new char[size][size];
for (int i = 0; i < size; i++) {
for (int j = 0; j < size; j++) {
outputGrid[i][j] = '-'; //initializing output grid with '-'
}
}
//Incase a different word from dictionary is created from random letters generated 
//We check grid using words from dictionary
for (int i = 0; i < 40; i++) {
for (int j = 0; j < dictionary.Hashtable[i].size(); j++) {
String word = dictionary.Hashtable[i].get(j);
check = searchWord(word);
if (check) {
check2.add(word);
}
}
}
}
//When user finds word he enters it and algorithm checks if the word is present
//Calculates score with total correct words 
void useranswers(int timeInSeconds) {
final int[] time = {timeInSeconds}; 
int maxscore = output.size(); 
System.out.println(ANSI_BLUE+"You have " + time[0] / 60 + " minutes to play. Timer starts now!"+ANSI_RESET);
Thread timerThread = new Thread(() -> { 
try { 
while (time[0] > 0) 
{ 
if (time[0] % 60 == 30 || time[0] % 60 == 0) 
{ 
System.out.print(ANSI_BLUE+"\nTime left: " + time[0] / 60 + " minutes " + (time[0] % 60) + " seconds\n"+ANSI_RESET);
} 
Thread.sleep(1000); 
time[0]--; 
} 
System.out.println("\nTime's up! To see how you performed enter 0"); 
synchronized (BoggleSolver5.class) 
{ 
BoggleSolver5.class.notify(); 
} 
} 
catch (InterruptedException e) {  
Thread.currentThread().interrupt(); 
} 
}
); 
timerThread.start(); 
synchronized (BoggleSolver5.class) { 
 word = null;
System.out.println("Enter \n0: Show Solution\n1: Undo");
while (score!=maxscore&&time[0]>0) {
flag = false;
word=in.next();
useranswers.push(word);
if(word.equals("0")) {
useranswers.pop();
checkScore(score);
System.out.println("Final Answer Grid:");
printOutputGrid();
timerThread.interrupt();
return;
}
else if(word.equals("1")) {
useranswers.pop();
useranswers.pop();
printable.pop();
}
else if (check2.contains(word.toLowerCase())) {
flag = true;
printable.push(flag);
score++;
}
else {
flag = false;
printable.push(flag);
}
}
try {
BoggleSolver5.class.wait();	
}
catch(InterruptedException e) {
Thread.currentThread().interrupt();
}
}
}
void checkScore(int score) {
int  maxscore = 0;
maxscore = output.size();
String word;
Boolean check;
System.out.println("Let's check answers: ");
while(printable.empty()==false&&useranswers.empty()==false) {
word = useranswers.pop();
check = printable.pop();
if(check==true) {
System.out.println(ANSI_GREEN + word + ANSI_RESET);	
}
else {
System.out.println(ANSI_RED + word + ANSI_RESET);	
}
}
System.out.println("Your score: " + score + "/" + maxscore);
}
}
public class Main {
public static void main(String[] args) {
Scanner sc = new Scanner(System.in);
BoggleSolver5 b = new BoggleSolver5();
int option=0,size;
do {
// Levels 
System.out.println("Select Level \n1: Easy(10X10)\n2: Medium(13X13)\n3: Hard(15X15)\n4: Exit game"); 
option = sc.nextInt();
switch(option) {
case 1:
{
size=10;
b.create(size);
b.search(size);
b.useranswers(1*60);
break; 
}
case 2:
{
size=13;
b.create(size);
b.search(size);
b.useranswers(3*60);
break; 
}
case 3:
{
size=15;
b.create(size);
b.search(size);
b.useranswers(4*60);
break; 
}
case 4:
{
System.out.println("Thanks for playing"); 
break;
}
default:{
System.out.println("Sorry!! wrong input please recheck"); 
}
}
}while(option!=4);
} 
}
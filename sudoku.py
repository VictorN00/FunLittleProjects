import numpy as np

def possible(y, x, n):
    global grid
    for i in range(9):
        if (grid[y][i] == n):
            return False
    for i in range(9):
        if (grid[i][x] == n):
            return False
    x0 = x // 3 * 3
    y0 = y // 3 * 3
    for i in range (3):
        for j in range (3):
            if (grid[y0+ i][x0 + j] == n):
                return False
    return True

def solve():
    global grid
    for y in range(9):
        for x in range(9):
            if (grid[y][x] == 0):
                for n in range(1, 10):
                    if (possible(y, x, n)):
                        grid[y][x] = n
                        solve()
                        grid[y][x] = 0
                return
    print(np.matrix(grid))
    input("More?")

grid = [[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0],
[0, 0, 0, 0, 0, 0, 0, 0, 0]]

print("Input your sudoku puzzle in row-major order:")
for y in range(9):
    row = input().split(" ")
    grid[y] = list(map(int, row))
        

solve()
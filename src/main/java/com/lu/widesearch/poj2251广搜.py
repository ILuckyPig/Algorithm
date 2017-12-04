# coding=utf-8
import Queue
# poj2251
# 广搜

maze = [
    [["S", 0, 0, 0, 0],
     [0, 1, 1, 1, 0],
     [0, 1, 1, 0, 0],
     [1, 1, 1, 0, 1]],
    [[1, 1, 1, 1, 1],
     [1, 1, 1, 1, 1],
     [1, 1, 0, 1, 1],
     [1, 1, 0, 0, 0]],
    [[1, 1, 1, 1, 1],
     [1, 1, 1, 1, 1],
     [1, 0, 1, 1, 1],
     [1, 1, 1, 1, "E"]],
]

mazeStep = [
    [[["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]]],
    [[["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]]],
    [[["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]],
     [["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"], ["X","X","X"]]],
]
# print mazeStep[0][0][0] == ["X","X","Y"]
s = [0, 0, 0]
e = [2, 3, 4]
def run(s, e, maze, mazeStep):
    queque = Queue.Queue()
    queque.put(s)
    while not queque.empty():
        x = queque.get()
        y1 = [x[0],x[1],x[2]]
        y2 = [x[0],x[1],x[2]]
        y3 = [x[0],x[1],x[2]]
        y4 = [x[0],x[1],x[2]]
        y5 = [x[0],x[1],x[2]]
        y6 = [x[0],x[1],x[2]]
        if x == e:
            break

        y1[2] = y1[2] - 1
        # print x[0],x[1],x[2]
        if check(y1, maze) and mazeStep[y1[0]][y1[1]][y1[2]] == ["X", "X", "X"]:
            print "走到",y1[0],y1[1],y1[2]
            queque.put(y1)
            mazeStep[y1[0]][y1[1]][y1[2]] = x
            # print mazeStep[y1[0]][y1[1]][y1[2]]


        y2[2] = y2[2] + 1
        # print x[0], x[1], x[2]
        if check(y2, maze) and mazeStep[y2[0]][y2[1]][y2[2]] == ["X", "X", "X"]:
            print "走到", y2[0], y2[1], y2[2]
            queque.put(y2)
            mazeStep[y2[0]][y2[1]][y2[2]] = x
            # print mazeStep[y2[0]][y2[1]][y2[2]]


        y3[1] = y3[1] - 1
        # print x[0], x[1], x[2]
        if check(y3, maze) and mazeStep[y3[0]][y3[1]][y3[2]] == ["X", "X", "X"]:
            print "走到", y3[0], y3[1], y3[2]
            queque.put(y3)
            mazeStep[y3[0]][y3[1]][y3[2]] = x
            # print mazeStep[y3[0]][y3[1]][y3[2]]


        y4[1] = y4[1] + 1
        # print y[0], y[1], y[2]
        if check(y4, maze) and mazeStep[y4[0]][y4[1]][y4[2]] == ["X", "X", "X"]:
            print "走到", y4[0], y4[1], y4[2]
            queque.put(y4)
            mazeStep[y4[0]][y4[1]][y4[2]] = x
            # print mazeStep[y4[0]][y4[1]][y4[2]]


        y5[0] = y5[0] - 1
        # print y[0], y[1], y[2]
        if check(y5, maze) and mazeStep[y5[0]][y5[1]][y5[2]] == ["X", "X", "X"]:
            print "走到", y5[0], y5[1], y5[2]
            queque.put(y5)
            mazeStep[y5[0]][y5[1]][y5[2]] = x
            # print mazeStep[y5[0]][y5[1]][y5[2]]
            

        y6[0] = y6[0] + 1
        # print y[0], y[1], y[2]
        if check(y6, maze) and mazeStep[y6[0]][y6[1]][y6[2]] == ["X", "X", "X"]:
            print "走到", y6[0], y6[1], y6[2]
            queque.put(y6)
            mazeStep[y6[0]][y6[1]][y6[2]] = x
            # print mazeStep[y6[0]][y6[1]][y6[2]]

    p = [e[0],e[1],e[2]]
    while p != s:
        p = mazeStep[p[0]][p[1]][p[2]]
        print p


def check(a, b):
    if a[0] < b.__len__() and a[0] >= 0 and a[1] < b[0].__len__() and a[1] >= 0 and a[2] < b[0][0].__len__() and a[2] >= 0:
        if maze[a[0]][a[1]][a[2]] == 1:
            return False
        else:
            return True
    else:
        return False
run(s, e, maze, mazeStep)
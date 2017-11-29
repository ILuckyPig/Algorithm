# coding=utf-8
# 两个字符串
# str1 = abcdef
# str2 = bcadxy
# 利用增加、删除、修改 str2 ——> str1，最短需要几步

str1 = "abcdef"
str2 = "bcadxy"

edit = [[i for i in range(str2.__len__()+1)] for j in range(str1.__len__()+1)]
print edit

for i in range(0, str1.__len__()+1):
    for j in range(0, str2.__len__()+1):
        if i == 0 and j == 0:
            edit[i][j] = 0
        elif i > 0 and j == 0:
            edit[i][j] = i
        elif i == 0 and j > 0:
            edit[i][j] = j
        elif i > 0 and j > 0:
            x = edit[i - 1][j] + 1
            y = edit[i][j - 1] + 1
            s1 = str1[i-1:i]
            s2 = str2[j-1:j]
            a = 0 if s1 == s2 else 1
            z = edit[i - 1][j - 1] + a
            edit[i][j] = min(x, y, z)
print edit
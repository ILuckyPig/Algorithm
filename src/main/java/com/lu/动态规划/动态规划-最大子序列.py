# coding=utf-8
# 两个字符串
# str1 = abcdef
# str2 = bcadxy
# 最长公共子序列
str1 = "abcdefh"
str2 = "adefuv"

m = [[i for i in range(str2.__len__()+1)] for j in range(str1.__len__()+1)]
# print m

for i in range(str1.__len__()+1):
    for j in range(str2.__len__()+1):
        if i == 0 and j == 0:
            m[i][j] = 0
        elif i == 0 and j > 0:
            m[i][j] = 0
        elif i > 0 and j == 0:
            m[i][j] = 0
        # 有两种情况
        # 如果 第i个字符 == 第j个字符
        #   m[i][j] = m[i-1][j-1]+1
        # 否则
        #   m[i][j-1]
        #   m[i-1][j]
        # 取两数最大值（因为是求最长子序列长度）
        # 最后edit[][]的左下角的值，为最短距离，即最少步骤
        elif i > 0 and j > 0:
            a = str1[i-1:i]
            b = str2[j-1:j]
            if a == b:
                m[i][j] = m[i-1][j-1] + 1
            else:
                m[i][j] = max(m[i][j-1], m[i-1][j])
print m
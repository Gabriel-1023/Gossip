import pandas as pd
from matplotlib import pyplot

data = pd.read_csv("src/out/节点个数-收敛轮数、误差.csv", header=0)
print(data)
x = data.values[:,0]
y1 = data.values[:,1]
y2 = data.values[:,2]

pyplot.figure(figsize=(6,7))
pyplot.rcParams['font.sans-serif'] = ['SimHei']
ax1 = pyplot.subplot(211)
ax1.set_title("节点个数与收敛轮数的关系")
pyplot.plot(x,y1,color='red',linewidth=2.0,linestyle='--')
ax2 = pyplot.subplot(212)
ax2.set_title("节点个数与误差关系")
pyplot.plot(x,y2,color='blue',linewidth=3.0,linestyle='-.')
pyplot.show()

data2 = pd.read_csv("src/out/k值-收敛轮数、误差.csv", header=0)
print(data2)
x = data2.values[:, 0]
y1 = data2.values[:, 1]
y2 = data2.values[:, 2]

pyplot.figure(figsize=(6,7))
pyplot.rcParams['font.sans-serif'] = ['SimHei']
ax1 = pyplot.subplot(211)
ax1.set_title("K与收敛轮数的关系")
pyplot.plot(x,y1,color='red',linewidth=2.0,linestyle='--')
ax2 = pyplot.subplot(212)
ax2.set_title("K与误差关系")
pyplot.plot(x,y2,color='blue',linewidth=3.0,linestyle='-.')
pyplot.show()
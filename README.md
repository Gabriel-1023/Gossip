# Gossip

运行src/Run_K_Rounds_Error.java，得到在相同节点个数（size=1000）下K值从1.2上升到20.8的运算结果。
结果包括k值，收敛轮数，误差

运行src/Run_Size_Rounds_Error.java，得到在相同K值(k=1.2)下节点个数从10上升到990的运算结果。
结果包括节点个数，收敛轮数，误差

输出结果保存在out内的 k值-收敛轮数、误差.csv 和 节点个数-收敛轮数、误差.csv 
在python环境下运行 python作图.py 来生成图表
python库环境 pandas matplotlib

电脑内存较小时，代码运行可能会出现卡顿，建议减小节点个数范围

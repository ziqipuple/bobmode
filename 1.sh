#!/bin/bash

# 进入项目根目录
cd /storage/emulated/0/AndroidIDEProjects/球球大作战模块/球球大作战模块绘制/

# 初始化 Git 仓库
if [ ! -d ".git" ]; then
    echo "Git 仓库尚未初始化，正在初始化..."
    git init
else
    echo "Git 仓库已存在。"
fi

# 配置 Git 用户信息
git config --global user.name "dunai123"
git config --global user.email "2865937179@qq.com"

# 添加远程仓库
git remote remove origin 2>/dev/null  # 防止已经存在相同的远程仓库
git remote add origin https://github.com/dunai123/dunainb.git

# 添加所有文件到 Git
git add .

# 提交文件
git commit -m "Initial commit"

# 推送到远程仓库
git push -u origin master

# 提示结束
echo "Git 操作完成，代码已推送到远程仓库。"

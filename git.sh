#!/usr/bin/env bash

# 批量修改git username 和 email
# 1. 拉取最新代码（可以使用git_pull）
# 2. 创建一个新的仓库
# 3. 修改并执行 git_update_username_email
# 4. 推送本地代码、分支、标签到远程（可以使用git_push）

# 拉去所有分支
git_pull() {
    git fetch --all
    git pull --all
}

# 批量修改
git_update_username_email() {
    git filter-branch -f --env-filter '
OLD_EMAIL="更正前邮箱"
CORRECT_NAME="更正的用户名"
CORRECT_EMAIL="更正的邮箱"
if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags
}

git_push() {
    # git push origin master --force 强行覆盖远程
    # 推送所有标签
    git push origin --tags
    # 推送所有分支
    git push REMOTE '*:*'
    git push REMOTE --all
    git push --all origin
}

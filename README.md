# TaskMaster
TaskMaster is the task-manager that is effected by Backlog.

## Latest goal
* to configure local environment.
- [x] docker
- [x] bootRun
- [x] DB migration

## 本日の振り返り
１日で環境設定が完了した。メモだらけのプロジェクトできったないが、ともあれ次回からapiの実装に入れるのがとても嬉しい。
一丁前にOAuth2に対応しようとしたが、一旦apiを自分で作れなければ意味がないので後から対応する。
1つ目のapiは`user_create`にする。それができればswaggerに対応して、PRを出したタイミングでgithub webにデプロイ、またDIが通るように対応したい。
そこまでしようとしたら2人日は必要になると想定している。
開発0日目、頑張ろう。


# Environment
Plz check `build.gradle.kts`.Thank u.

# Implemented contents
Nothing

# Future implementation contents
* User
  * create
  * delete
  * update
    * pass
    * id
* Login
* Logout
* Task management
  * task-register
  * tasks-show
  * task-search
  * task-detail
  * task-delete
  * task-update
  * task-complete

wikipedia機能はTaskManagementAPIとほぼ変わらないので、ライブラリを変えて作ってみる。そのためプラスアルファとしての目標
* Wikipedia for myself
  * word-register
  * words-show
  * word-search
  * detail-show
  * word-delete
  * word-update
  * word-complete

# Dev sycle
1. make domain object: DB's objects
2. make DTA: request/response body,model
3. make UseCase
4. make Controller
5. implement UseCase
6. make Repository
7. implement Repository
8. UnitTest


# Slick 型指定テスト

## 起動方法

1. [MariaDB](https://mariadb.org/) を起動
    ```
    docker-compose up -d
    ```
1. Server起動
    ```
    sbt run
    ```


## 開発

### DBアクセス用コードの自動生成

1. [MariaDB](https://mariadb.org/) を起動
    ```
    docker-compose up -d
    ```
1. 自動生成
    ```
    sbt slick-codegen/run
    ```


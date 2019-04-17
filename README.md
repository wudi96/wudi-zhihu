### 知乎精华

environment

```text
Java
Redis
Mongodb
```

1、根据topicTitle查询topicId

POST http://localhost:8080/create
```json
{
    "topicTitle": "计算机"
}
```

```json
{
    "returnCode": "0",
    "data": {
        "returnCode": "0",
        "data": {
            "topicId": "19555547",
            "topicTitle": "计算机"
        },
        "message": ""
    },
    "message": ""
}
```

2、根据topicTitle、topicId查询话题摘要

POST http://localhost:8080/summary
```json
{
    "topicId": "19555547",
    "topicTitle": "计算机"
}
```
```json
{
    "returnCode": "0",
    "data": {
        "returnCode": "0",
        "data": {
            "topicId": "19555547",
            "topicTitle": "计算机",
            "topicAnswerNum": "490,831",
            "topicUrl": "https://www.zhihu.com/topic/19555547/top-answers/",
            "topicAttentionNum": "204,851"
        },
        "message": ""
    },
    "message": ""
}
```

3、根据topicTitle、topicId、topicAnswerNum抓取知乎精华

POST http://localhost:8080/crawler
```json
{
    "topicId": "19555547",
    "topicTitle": "计算机",
    "topicAnswerNum":"1"
}
```
```json
{
    "returnCode": "0",
    "data": {
        "returnCode": "0",
        "data": {
            "status": "rushing"
        },
        "message": ""
    },
    "message": ""
}
```
4、根据topicTitle、topicId、topicAnswerNum查询抓取状态

POST http://localhost:8080/status
```json
{
    "topicId": "19555547",
    "topicTitle": "计算机",
    "topicAnswerNum":"1"
}
```
```json
{
    "returnCode": "0",
    "data": {
        "returnCode": "0",
        "data": "success",
        "message": ""
    },
    "message": ""
}
```
5、根据topicTitle、topicId获取抓取结果

POST http://localhost:8080/result
```json
{
    "topicId": "19555547",
    "topicTitle": "计算机"
}
```
```json
{
    "returnCode": "0",
    "data": {
        "returnCode": "0",
        "data": {
            "topic": "计算机",
            "topicContents": [
                {
                    "question": {
                        "id": "",
                        "url": "",
                        "crawlTime": "",
                        "title": "",
                        "commentCount": "",
                        "stareCount": "",
                        "browseCount": "",
                        "tags": [
                            "",
                            ""
                        ],
                        "detail": ""
                    },
                    "answer": [
                        {
                            "id": "",
                            "url": "",
                            "voteCount": "",
                            "commentCount": "",
                            "createTime": "",
                            "modifyTime": "",
                            "content": ""
                        }
                    ],
                    "topicId": "",
                    "questionAnswerId": ""
                }
            ]
        },
        "message": ""
    },
    "message": ""
}
```
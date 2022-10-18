package com.example.movies.Common

import com.example.movies.*

val comics = ComicList(12,"http://gateway.marvel.com/v1/public/characters/1011334/comics")
val comics2 = ComicList(4,"http://gateway.marvel.com/v1/public/characters/1017100/comics")

val thumbnail = Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784", "jpg")
val thumbnail2 = Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16", "jpg")
val result1 = Result(1011334, "3-D Man", "", "2014-04-29T14:18:17-0400", thumbnail,"http://gateway.marvel.com/v1/public/characters/1011334",comics)
val result2 = Result(1017100, "A-Bomb (HAS)", "Rick Jones has been Hulk's best bud since day one, but now he's more than a friend...he's a teammate! Transformed by a Gamma energy explosion, A-Bomb's thick, armored skin is just as strong and powerful as it is blue. And when he curls into action, he uses it like a giant bowling ball of destruction! ",
        "2013-09-18T15:54:04-0400", thumbnail2,"http://gateway.marvel.com/v1/public/characters/1017100",comics2)

val result = listOf(result1,result2)
val data = Data(0, 2, 1559, 2, result)

fun charactersPOJOModel() = Characters(
        200, "OK", "© 2022 MARVEL",
        "Data provided by Marvel. © 2022 MARVEL",
        "<a href=\"http://marvel.com\">Data provided by Marvel. © 2022 MARVEL</a>",
        "d410226f5ac4473e4429c7310d446dfbf6175bd0", data)



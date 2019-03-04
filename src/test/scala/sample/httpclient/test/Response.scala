package sample.httpclient.test


case class User(user_id:Int = 0,name:String,age:Int,programming_skills:List[ProgrammingSkill])
case class ProgrammingSkill(item:String)
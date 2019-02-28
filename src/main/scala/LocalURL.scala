
object LocalURL extends App{

  def createURI(baseURL: String,organizationID :Int): String ={

    s"$baseURL/organization/$organizationID/users"
  }
  def updateURI(baseURL: String,organizationID :Int,userID :Int): String ={

    s"$baseURL/organization/$organizationID/users/$userID"
  }
  def deleteURI(baseURL: String,organizationID :Int,userID :Int): String ={

    s"$baseURL/organization/$organizationID/users/$userID"
  }
  def retrieveURI(baseURL: String,organizationID :Int,userID :Int): String ={

    s"$baseURL/organization/$organizationID/users/$userID"
  }
  def listURI(baseURL: String,organizationID :Int): String ={

    s"$baseURL/organization/$organizationID/users"
  }
}
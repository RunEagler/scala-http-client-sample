package http.client

object LocalURL extends App{

  def createURI(baseURL: String,organizationID :Int): String ={

    s"$baseURL/organizations/$organizationID/users"
  }
  def updateURI(baseURL: String,organizationID :Int,userID :Int): String ={

    s"$baseURL/organizations/$organizationID/users/$userID"
  }
  def deleteURI(baseURL: String,organizationID :Int,userID :Int): String ={

    s"$baseURL/organizations/$organizationID/users/$userID"
  }
  def retrieveURI(baseURL: String,organizationID :Int,userID :Int): String ={

    s"$baseURL/organizations/$organizationID/users/$userID"
  }
  def listURI(baseURL: String,organizationID :Int): String ={

    s"$baseURL/organizations/$organizationID/users"
  }
}

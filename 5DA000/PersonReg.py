# -*- coding: latin-1 -*-
import sqlite3
from Person import Person
import cgitb
cgitb.enable()

class PersonReg:
        """The most important part pf the assigment, handles all interactions
        with the SQL database. Most often called from the the cgi files
        and in rare cases in the page objects to fetch notes."""

        def __init__(self, dbname):
                '''
                Checks if there is a table with the name "Agents" and "Notes",
                and creates a .db file with a given name, containing the tables
                of the same names.
                "Agents" will hold information amout a person, and when called,
                will create a Person object with the information containing in
                the database table. While "Notes" table will contain the id of
                the Person to which the note belongs.
                '''
                self.db=sqlite3.connect(dbname)
                cursor=self.db.cursor()
                cursor.execute("""CREATE TABLE if not exists `Agents` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT,
                `name`  TEXT,
                `lastname`      TEXT,
                `email` TEXT);""")
                cursor.execute("""CREATE TABLE if not exists `Notes` (  `id` INTEGER PRIMARY KEY AUTOINCREMENT,
                `person_id` TEXT,
                `title` TEXT,
                `note`  TEXT);""")
                self.db.commit()

        def search(self, searchTerm):
                '''
                Return ID numbers in a list if the given string matches anything
                in the database.
                '''
                cursor=self.db.cursor()
                result=[]
                for idnr in cursor.execute("select id from Agents where name or lastname or email like '%"+searchTerm+"%'"):
                        result.append(idnr)
                return result

        def getAgent(self, agent_id):
                '''
                Returns all information about an agent from the database with
                the given id number, contained in a Person object.
                '''
                cursor=self.db.cursor()
                res = cursor.execute("select id, name, lastname, email from Agents where id = %s" % agent_id).fetchone()
                agent = Person(res[0],res[1],res[2],res[3])
                return agent

        def addAgent(self, name, lastname, email):
                '''

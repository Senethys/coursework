# -*- coding: latin-1 -*-
from WebPage import WebPage
from PersonReg import PersonReg
import sqlite3
import cgitb
cgitb.enable()

class MemberList(WebPage):
        '''
        The "Main" page of the site. Creates a table of all the members
        in the databasewith checkboxes in order to delete checked members.
        Also includes a form toadd more members and a note with a title.
        '''
        def printBody(self):
                print """<form class = "delAgent" action="deleteAgent.cgi"
                method = "post" id = "checkvals"> <input type="submit" value="Delete">
                </form>"""

                print """<table class = "memberlist" >"""
                register=PersonReg("database.db")
                agentlist = register.search("")
                for agent_id in agentlist:
                        agent = register.getAgent(agent_id)
                        print "<tr><td>"
                        print '<input type= "checkbox" name="checkid" form = "checkvals" value = %d>' % agent.getId()
                        print "</td><td>"
                        print "<a href='agent_info.cgi?agentid=%d'>" % agent.getId()
                        print agent.getName().encode("Latin-1")
                        print "</a>"
                        print "</td><td>"
                        print "<a href='agent_info.cgi?agentid=%d'>" % agent.getId()
                        print agent.getLastname()
                        print "</a>"
                        print "</td><td>"
                        print "<a href='agent_info.cgi?agentid=%d'>" % agent.getId()
                        print agent.getEmail()
                        print "</a>"
                        print "</td></tr>"
                print "</table>"
                print "<br>"

                print
                print "<form class = 'addAgent' action='addAgent.cgi' method='post'>"
                print "<h2 class= 'NewMemb'>Add new member:</h2>"
                print ("<label>First name: </label> <input type='text' name='fname'>")
                print ("<label>Last name:</label> <input type='text' name='lname'>")
                print ("<label>Email adress:</label><input type='text' name='mail'>")
                print "<br> </br> <br> </br>"
                print ("<label>Title:</label> <input type='text' name='note_title'>")

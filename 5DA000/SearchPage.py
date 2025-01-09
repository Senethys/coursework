# -*- coding: latin-1 -*-
from WebPage import WebPage
from PersonReg import PersonReg
import cgitb
cgitb.enable()


class SearchPage(WebPage):
        """This object is created when a user clicks the "search" button.
        Displays all the members if field is left empty"""

        def setSearchTerm(self,searchTerm):
                '''Converts the string entered in the form to SearchPage object, and
                strips the whitespaces'''
                self.searchTerm= str.strip(searchTerm)

        def printBody(self):
                '''
                Prints all agents in a table if anything is found similar to the
                search term.
                '''
                print "<h1>"
                print self.title
                print "</h1><table class = 'memberlist'>"
                register = PersonReg("database.db")
                agent_id_list = register.search(self.searchTerm)
                for agent_id in agent_id_list:
                        agent = register.getAgent(agent_id)
                        print "<tr>"
                        print "<td>"
                        print "<a href='agent_info.cgi?agentid=%d'>" % agent.getId()
                        print agent.getName()
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

                print """<form class = 'BackButtonMemberList' action = 'index.cgi'
                method = 'get'> <input type = 'submit' value = 'Back'></form>"""

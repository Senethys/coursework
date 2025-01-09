# -*- coding: latin-1 -*-
from PersonReg import PersonReg
from WebPage import WebPage
import cgitb
cgitb.enable()

class EditAgent(WebPage):
        '''A simple page that displays all information known about a person in
        editable text fields each for name, lastname and email address.
        A delete and Save Changes button is also present.'''
        def printBody(self):
                print "<form class = 'delAgent' action='deleteAgent.cgi?id=%s' method='post'>" % self.agent.getId()
                print "<input type='submit' value='Delete %s %s'>" % (self.agent.getName(), self.agent.getLastname())
                print "<input type='hidden' value='2' name = 'button'> </form>"

                print "<form class = 'addAgent' action='saveChanges.cgi?id=%s' method = 'post'>" % self.agent.getId()
                print "First name: <input type='text' name='fname' value='%s'>" % self.agent.getName()
                print "Last name: <input type='text' name='lname' value='%s'>" % self.agent.getLastname()
                print "Email: <input type='text' name='mail' value='%s'> <br> </br> <br> </br>" % self.agent.getEmail()
                print "<input type='hidden' name = 'button' value='0'>"
                print "<input type='submit' value='Save Changes'> </form>"

                print """<form class = 'BackButtonMemberList' action = 'agent_info.cgi'
                method = 'get'> <input type = 'submit' value = 'Back'>
                <input type = 'hidden' name = 'agentid' value = '%s'> </form>""" % self.agent.getId()


        def setAgent(self, agent_id):
                self.registry = PersonReg("database.db")
                self.agent = self.registry.getAgent(agent_id)

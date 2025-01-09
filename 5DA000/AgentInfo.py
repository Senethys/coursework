
# -*- coding: latin-1 -*-

from PersonReg import PersonReg
from WebPage import WebPage
import cgitb
cgitb.enable()

class AgentInfo(WebPage):
        '''
        Prints out all information about the clicked person, including all
        notes in a table form. Includes delete and delete all notes buttons.
        Also an edit button that lets the user edit Personal information
        (name, lastname, email).
        '''
        def printBody(self):
                print "<div class = 'AgentInfo'>"
                print "<h2> %s, %s </h2>" % (self.agent.getName(), self.agent.getLastname())
                print "<p>Email: %s </p>" % (self.agent.getEmail())
                print "</div>"
                print "<form class = 'delAgent' action='deleteAgent.cgi?id=%s' method='post'>" % self.agent.getId()
                print "<input type='submit' value='Delete %s %s'>" % (self.agent.getName(), self.agent.getLastname())
                print "<input type='hidden' value='2' name = 'button'> </form>"

                print "<form class = 'Edit' action='editAgent.cgi?id=%s' method='post'>" % self.agent.getId()
                print "<input type='submit' value='Edit'> </form>"


                for note in self.printAllNotes():
                        print """<a method = 'post' href='editNote.cgi?note_id=%d&agent_id=%s' class = 'notetitle'> <b>%s</b> </a>
                        <p class= 'notetext'><b>%s</b></p>""" % (note[0], self.agent.getId(), note[2].encode('latin-1'), note[3].encode('latin-1'))

                print """<form class = 'BackButtonMemberList' action = 'memberlist.cgi'
                method = 'get'> <input type = 'submit' value = 'Back'></form>"""

                print """<form class='AddNote' action="addNote.cgi?id=%d" method = "post">
                <input type="submit" value="Add note"></form> """ % self.agent.getId()
                print """<form class='DeleteNotes' action = 'saveChanges.cgi?agent_id=%d' method = 'post'>
                <input type='hidden' value='5' name = 'button'>
                <input type='submit' value='Delete All Notes'></form>""" % self.agent.getId()

        def setAgent(self, agent_id):
                '''
                Fetches Person object from the database so that all information
                can be displayed about the Person
                '''

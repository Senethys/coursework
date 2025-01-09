# -*- coding: latin-1 -*-

from PersonReg import PersonReg
from WebPage import WebPage
import cgitb
cgitb.enable()

class AddNote(WebPage):
        '''
        Prints out two fields of the Detail view/Agentinfo
        page, one for title and for note text. They appear to the left,
        below the persson's name of whoom the note will be added to
        '''

        def printBody(self):
                print "<div class = 'AgentInfo'>"
                print "<h2> %s, %s </h2>" % (self.agent.getName(), self.agent.getLastname())
                print "<p>Email: %s </p>" % (self.agent.getEmail())
                print "</div>"

                print "<form class = 'delAgent' action='deleteAgent.cgi?id=%s' method='post'>" % self.agent.getId()
                print "<input type='submit' value='Delete %s %s'>" % (self.agent.getName(), self.agent.getLastname())
                print "<input type='hidden' value='2' name = 'button'> </form>"

                print "<form class = 'Edit' action='editAgent.cgi?id=%s' method='post' id = 'checkid'>" % self.agent.getId()
                print "<input type='submit' value='Edit'> </form>"

                for note in self.printAllNotes():
                        print """<a method = 'post' href='editNote.cgi?note_id=%d&agent_id=%s' class = 'notetitle'> <b>%s</b> </a>
                        <p class= 'notetext'><b>%s</b></p>""" % (note[0], self.agent.getId(), note[2].encode('latin-1'), note[3].encode('latin-1'))

                print """<form class = "AddNoteForm" action='saveChanges.cgi' method='post'>
                <label>Title:</label>  <input type = 'text' name = 'note_title'>
                <label>Note:</label>   <textarea rows="15" cols="28" name = 'note_text' ></textarea>
                <input type='submit' value='Save Note'>
                <input type='hidden' name ='button' value = '1'>
                <input type='hidden' name = 'id' value = '%d'></form>""" % self.agent.getId()

                print """<form class = 'BackButtonMemberList'
                action = 'memberlist.cgi' method = 'get'>
                <input type = 'submit' value = 'Back'></form>"""

        def setAgent(self, agent_id):
                '''
                Fetches a Person object from the database so
                that the note can be added to the person and so that other
                information on the page can remain, such as old notes

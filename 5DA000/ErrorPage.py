# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from WebPage import WebPage


class ErrorPage(WebPage):
        '''Error page is triggered by except, present in every cgi file.
        Prints error message and a "Back" Button.'''

        def printBody(self):
                print "<h1>"
                print "An error has occured :("
                print "</h2>"
                print """<p> The server encountered an error,
                most likely due to invalid data entered in the link field
                or unsupported characters</p>"""
                print "<p> Go back and try again. </p>"

                print """<form class = 'BackButtonMemberList' action = 'index.cgi'
                method = 'get'> <input type = 'submit' value = 'Back'></form>"""
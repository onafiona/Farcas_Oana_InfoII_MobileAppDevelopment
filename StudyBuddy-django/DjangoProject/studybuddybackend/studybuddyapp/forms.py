from django import forms
from .models import Materie, Tema, Proiect, Examen

# Formular pentru Teme
class TemaForm(forms.ModelForm):
    class Meta:
        model = Tema
        fields = ['titlu', 'descriere', 'deadline', 'materie']
        widgets = {
            'deadline': forms.DateTimeInput(attrs={'type': 'datetime-local'}),  # pentru un input ușor de folosit în formular
        }

# Formular pentru Proiecte
class ProiectForm(forms.ModelForm):
    class Meta:
        model = Proiect
        fields = ['titlu', 'descriere', 'deadline', 'materie']
        widgets = {
            'deadline': forms.DateTimeInput(attrs={'type': 'datetime-local'}),
        }

# Formular pentru Examene
class ExamenForm(forms.ModelForm):
    class Meta:
        model = Examen
        fields = ['descriere', 'data_examen', 'materie']
        widgets = {
            'data_examen': forms.DateTimeInput(attrs={'type': 'datetime-local'}),
        }

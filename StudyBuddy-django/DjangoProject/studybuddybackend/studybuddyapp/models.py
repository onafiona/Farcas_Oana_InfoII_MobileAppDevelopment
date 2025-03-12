from django.db import models
from django.utils import timezone

class Materie(models.Model):
    nume = models.CharField(max_length=200, unique=True)
    descriere = models.TextField(blank=True)

    def __str__(self):
        return self.nume

class Tema(models.Model):
    titlu = models.CharField(max_length=150)
    descriere = models.TextField()
    deadline = models.DateTimeField()
    materie = models.ForeignKey(Materie, on_delete=models.CASCADE)

    def __str__(self):
        return self.titlu

    def is_due(self):
        return self.deadline < timezone.now()

class Proiect(models.Model):
    titlu = models.CharField(max_length=200)
    descriere = models.TextField()
    deadline = models.DateTimeField()
    materie = models.ForeignKey(Materie, on_delete=models.CASCADE)

    def __str__(self):
        return self.titlu

    def is_due(self):
        return self.deadline < timezone.now()

class Examen(models.Model):
    materie = models.ForeignKey(Materie, on_delete=models.CASCADE)
    descriere = models.TextField()
    data_examen = models.DateTimeField()

    def __str__(self):
        return f"Examen la {self.materie.nume} pe {self.data_examen.strftime('%d-%m-%Y')}"

from django.shortcuts import render, get_object_or_404, redirect
from .models import Materie, Tema, Proiect, Examen
from rest_framework import viewsets
from .serializers import MaterieSerializer, TemaSerializer, ProiectSerializer, ExamenSerializer

class MaterieViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Materie.objects.all()
    serializer_class = MaterieSerializer

class TemaViewSet(viewsets.ModelViewSet):
    queryset = Tema.objects.all()
    serializer_class = TemaSerializer

class ProiectViewSet(viewsets.ModelViewSet):
    queryset = Proiect.objects.all()
    serializer_class = ProiectSerializer

class ExamenViewSet(viewsets.ModelViewSet):
    queryset = Examen.objects.all()
    serializer_class = ExamenSerializer


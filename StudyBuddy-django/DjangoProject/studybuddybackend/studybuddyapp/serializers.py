from rest_framework import serializers
from .models import Materie, Tema, Proiect, Examen

class MaterieSerializer(serializers.ModelSerializer):
    class Meta:
        model = Materie
        fields = ['id', 'nume', 'descriere']

class TemaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tema
        fields = ['id', 'titlu', 'descriere', 'deadline', 'materie']

class ProiectSerializer(serializers.ModelSerializer):
    class Meta:
        model = Proiect
        fields = ['id', 'titlu', 'descriere', 'deadline', 'materie']

class ExamenSerializer(serializers.ModelSerializer):
    materie_nume = serializers.CharField(source='materie.nume', read_only=True)
    class Meta:
        model = Examen
        fields = ['id', 'descriere', 'data_examen', 'materie', 'materie_nume']

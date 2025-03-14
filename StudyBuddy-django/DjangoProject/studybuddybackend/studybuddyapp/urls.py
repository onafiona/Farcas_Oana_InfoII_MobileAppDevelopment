from django.urls import path, include
from . import views
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register(r'materii', views.MaterieViewSet, basename='materie')
router.register(r'teme', views.TemaViewSet, basename='tema')
router.register(r'proiecte', views.ProiectViewSet, basename='proiect')
router.register(r'examene', views.ExamenViewSet, basename='examen')

urlpatterns=[
    path('', include(router.urls)),
]


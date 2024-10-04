import { NativeSyntheticEvent, ViewStyle } from 'react-native';

// Definir el tipo de evento para el clic en los marcadores
export type MarkerClickEvent = NativeSyntheticEvent<{
  latitude: number;
  longitude: number;
  title: string;
}>;

export interface AwesomeJeqMapsViewProps {
  markerData: {
    latitude: number;
    longitude: number;
    title: string;
    description: string;
  }[];
  style?: ViewStyle;

  onMarkerClick?: (event: MarkerClickEvent) => void;
}
